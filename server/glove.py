import logging
from collections import Counter
from math import log
from random import shuffle

import numpy as np
from matplotlib import pyplot as plt
from scipy import sparse
from scipy.spatial.distance import pdist
from sklearn.decomposition import PCA


class GloVe:

    def __init__(self, lists, learning_rate=0.05, x_max=100, alpha=0.75, vector_size=100, iterations=25, min_count=None,
                 logging_level=logging.INFO):
        self._lists = lists
        self._learning_rate = learning_rate
        self._x_max = x_max
        self._alpha = alpha
        self._vector_size = vector_size
        self._iterations = iterations
        self._min_count = min_count

        self._logging_level = logging_level
        self._logger = None

        self._vocabulary = None
        self.id2word = None

    def setup(self):
        self._setup_logger()
        self._build_vocab()
        self._build_id2word()

    def fit(self):
        space = self._train()
        merged_space = self._merge_main_context(space)

        return merged_space

    def _setup_logger(self):
        # TODO redirect to file
        self._logger = logging.getLogger('GloVe')

        stream_logger = logging.StreamHandler()
        stream_logger.setLevel(self._logging_level)

        self._logger.addHandler(stream_logger)

    def _build_vocab(self):
        """
        Build a vocabulary with word frequencies for an entire corpus.

        Returns a dictionary `w -> (i, f)`, mapping word strings to pairs of
        word ID and word corpus frequency.
        """

        self._logger.info("Building vocab from corpus")

        vocab = Counter()
        for list_ in self._lists:
            vocab.update(list_)

        self._logger.info("Done building vocab from corpus.")

        self._vocabulary = {word: (i, freq) for i, (word, freq) in enumerate(vocab.items())}

    def _build_id2word(self):
        self.id2word = dict((id_, word) for word, (id_, _) in self._vocabulary.items())

    def _build_cooccur(self):
        """
        Build a word co-occurrence list for the given corpus.

        This function is a tuple generator, where each element (representing
        a cooccurrence pair) is of the form

            (i_main, i_context, cooccurrence)

        where `i_main` is the ID of the main word in the cooccurrence and
        `i_context` is the ID of the context word, and `cooccurrence` is the
        `X_{ij}` cooccurrence value as described in Pennington et al.
        (2014).

        If `min_count` is not `None`, cooccurrence pairs where either word
        occurs in the corpus fewer than `min_count` times are ignored.
        """

        vocab_size = len(self._vocabulary)

        # Collect cooccurrences internally as a sparse matrix for passable
        # indexing speed; we'll convert into a list later
        cooccurrences = sparse.lil_matrix((vocab_size, vocab_size),
                                          dtype=np.float64)

        for i, list_ in enumerate(self._lists):
            if i % 1000 == 0:
                self._logger.info("Building cooccurrence matrix: on line %i", i)

            token_ids = [self._vocabulary[word][0] for word in list_]

            for center_i, center_id in enumerate(token_ids):
                # Collect all word IDs in left window of center word
                context_ids = token_ids[:]
                del context_ids[center_i]
                contexts_len = len(context_ids)

                for left_i, left_id in enumerate(context_ids):
                    # Build co-occurrence matrix symmetrically (pretend we
                    # are calculating right contexts as well)
                    cooccurrences[center_id, left_id] += 0.5
                    cooccurrences[left_id, center_id] += 0.5

        # Now yield our tuple sequence (dig into the LiL-matrix internals to
        # quickly iterate through all nonzero cells)
        for i, (row, data) in enumerate(zip(cooccurrences.rows,
                                            cooccurrences.data)):
            if self._min_count is not None and self._vocabulary[self.id2word[i]][1] < self._min_count:
                continue

            for data_idx, j in enumerate(row):
                if self._min_count is not None and self._vocabulary[self.id2word[j]][1] < self._min_count:
                    continue

                yield i, j, data[data_idx]

    def _run_iter(self, data):
        """
        Run a single iteration of GloVe training using the given
        cooccurrence data and the previously computed weight vectors /
        biases and accompanying gradient histories.

        `data` is a pre-fetched data / weights list where each element is of
        the form

            (v_main, v_context,
             b_main, b_context,
             gradsq_W_main, gradsq_W_context,
             gradsq_b_main, gradsq_b_context,
             cooccurrence)

        as produced by the `train_glove` function. Each element in this
        tuple is an `ndarray` view into the data structure which contains
        it.

        See the `train_glove` function for information on the shapes of `W`,
        `biases`, `gradient_squared`, `gradient_squared_biases` and how they
        should be initialized.

        The parameters `x_max`, `alpha` define our weighting function when
        computing the cost for two word pairs; see the GloVe paper for more
        details.

        Returns the cost associated with the given weight assignments and
        updates the weights by online AdaGrad in place.
        """

        global_cost = 0

        # We want to iterate over data randomly so as not to unintentionally
        # bias the word vector contents
        shuffle(data)

        for (v_main, v_context, b_main, b_context, gradsq_W_main, gradsq_W_context,
             gradsq_b_main, gradsq_b_context, cooccurrence) in data:
            weight = (cooccurrence / self._x_max) ** self._alpha if cooccurrence < self._x_max else 1

            # Compute inner component of cost function, which is used in
            # both overall cost calculation and in gradient calculation
            #
            #   $$ J' = w_i^Tw_j + b_i + b_j - log(X_{ij}) $$
            cost_inner = (v_main.dot(v_context)
                          + b_main[0] + b_context[0]
                          - log(cooccurrence))

            # Compute cost
            #
            #   $$ J = f(X_{ij}) (J')^2 $$
            cost = weight * (cost_inner ** 2)

            # Add weighted cost to the global cost tracker
            global_cost += 0.5 * cost

            # Compute gradients for word vector terms.
            #
            # NB: `main_word` is only a view into `W` (not a copy), so our
            # modifications here will affect the global weight matrix;
            # likewise for context_word, biases, etc.
            grad_main = weight * cost_inner * v_context
            grad_context = weight * cost_inner * v_main

            # Compute gradients for bias terms
            grad_bias_main = weight * cost_inner
            grad_bias_context = weight * cost_inner

            # Now perform adaptive updates
            v_main -= (self._learning_rate * grad_main / np.sqrt(gradsq_W_main))
            v_context -= (self._learning_rate * grad_context / np.sqrt(gradsq_W_context))

            b_main -= (self._learning_rate * grad_bias_main / np.sqrt(gradsq_b_main))
            b_context -= (self._learning_rate * grad_bias_context / np.sqrt(
                gradsq_b_context))

            # Update squared gradient sums
            gradsq_W_main += np.square(grad_main)
            gradsq_W_context += np.square(grad_context)
            gradsq_b_main += grad_bias_main ** 2
            gradsq_b_context += grad_bias_context ** 2

        return global_cost

    def _train(self, iter_callback=None):
        """
        Train GloVe vectors on the given generator `cooccurrences`, where
        each element is of the form

            (word_i_id, word_j_id, x_ij)

        where `x_ij` is a cooccurrence value $X_{ij}$ as presented in the
        matrix defined by `build_cooccur` and the Pennington et al. (2014)
        paper itself.

        If `iter_callback` is not `None`, the provided function will be
        called after each iteration with the learned `W` matrix so far.

        Keyword arguments are passed on to the iteration step function
        `run_iter`.

        Returns the computed word vector matrix `W`.
        """

        vocab_size = len(self._vocabulary)

        # Word vector matrix. This matrix is (2V) * d, where N is the size
        # of the corpus vocabulary and d is the dimensionality of the word
        # vectors. All elements are initialized randomly in the range (-0.5,
        # 0.5]. We build two word vectors for each word: one for the word as
        # the main (center) word and one for the word as a context word.
        #
        # It is up to the client to decide what to do with the resulting two
        # vectors. Pennington et al. (2014) suggest adding or averaging the
        # two for each word, or discarding the context vectors.
        W = (np.random.rand(vocab_size * 2, self._vector_size) - 0.5) / float(self._vector_size + 1)

        # Bias terms, each associated with a single vector. An array of size
        # $2V$, initialized randomly in the range (-0.5, 0.5].
        biases = (np.random.rand(vocab_size * 2) - 0.5) / float(self._vector_size + 1)

        # Training is done via adaptive gradient descent (AdaGrad). To make
        # this work we need to store the sum of squares of all previous
        # gradients.
        #
        # Like `W`, this matrix is (2V) * d.
        #
        # Initialize all squared gradient sums to 1 so that our initial
        # adaptive learning rate is simply the global learning rate.
        gradient_squared = np.ones((vocab_size * 2, self._vector_size),
                                   dtype=np.float64)

        # Sum of squared gradients for the bias terms.
        gradient_squared_biases = np.ones(vocab_size * 2, dtype=np.float64)

        # Build a reusable list from the given cooccurrence generator,
        # pre-fetching all necessary data.
        #
        # NB: These are all views into the actual data matrices, so updates
        # to them will pass on to the real data structures
        #
        # (We even extract the single-element biases as slices so that we
        # can use them as views)
        data = [(W[i_main], W[i_context + vocab_size],
                 biases[i_main: i_main + 1],
                 biases[i_context + vocab_size: i_context + vocab_size + 1],
                 gradient_squared[i_main], gradient_squared[i_context + vocab_size],
                 gradient_squared_biases[i_main: i_main + 1],
                 gradient_squared_biases[i_context + vocab_size
                                         : i_context + vocab_size + 1],
                 cooccurrence)
                for i_main, i_context, cooccurrence in self._build_cooccur()]

        for i in range(self._iterations):
            if (i + 1) % 500 == 0:
                self._logger.info("\tBeginning iteration %i..", i + 1)

            cost = self._run_iter(data)

            if (i + 1) % 500 == 0:
                self._logger.info("\t\tDone (cost %f)", cost)

            if iter_callback is not None:
                iter_callback(W)

        return W

    def _merge_main_context(self, W, merge_fun=lambda m, c: np.mean([m, c], axis=0),
                            normalize=True):
        """
        Merge the main-word and context-word vectors for a weight matrix
        using the provided merge function (which accepts a main-word and
        context-word vector and returns a merged version).

        By default, `merge_fun` returns the mean of the two vectors.
        """

        vocab_size = int(len(W) / 2)
        for i, row in enumerate(W[:vocab_size]):
            merged = merge_fun(row, W[i + vocab_size])
            if normalize:
                merged /= np.linalg.norm(merged)
            W[i, :] = merged

        return W[:vocab_size]


if __name__ == '__main__':
    pass
    test_lists = [
        ['owoce', 'szynka'],
        ['owoce', 'szynka'],
        ['owoce', 'szynka'],
        ['owoce', 'szynka'],
        ['owoce', 'szynka'],
        ['owoce', 'szynka'],
        ['owoce', 'szynka'],
        ['owoce', 'szynka'],
        ['woda', 'chleb'],
        ['woda', 'chleb'],
        ['woda', 'chleb'],
        ['woda', 'chleb'],
        ['woda', 'chleb'],
        ['woda', 'chleb'],
        ['woda', 'chleb'],
        ['woda', 'chleb'],
        ['kawa', 'ciastka'],
        ['kawa', 'ciastka'],
        ['kawa', 'ciastka'],
        ['kawa', 'ciastka'],
        ['kawa', 'ciastka'],
        ['kawa', 'ciastka'],
        ['kawa', 'ciastka'],
        ['kawa', 'ciastka'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
    ]
    shuffle(test_lists)

    # TODO tune parameters
    glove = GloVe(test_lists, learning_rate=0.001, vector_size=10, iterations=2000)

    glove.setup()
    result = glove.fit()

    distance_vector = pdist(result)

    pca = PCA(n_components=2)
    pca_result = pca.fit_transform(result)

    xs = pca_result[:, 0]
    ys = pca_result[:, 1]

    _, ax = plt.subplots()
    ax.scatter(xs, ys)

    for i in range(len(pca_result)):
        name = glove.id2word[i]
        ax.annotate(name, (xs[i], ys[i]))

    plt.show()
