from datetime import datetime
from random import shuffle

from scipy.spatial.distance import pdist

from glove import GloVe

LEARNING_RATE = 0.05
X_MAX = 100
ALPHA = 0.75
V_SIZE = 100
ITERATIONS = 25


class Recommender:
    """
    Recommender used for training GloVe models on shopping lists


    Attributes:
        distance - currently trained distance vector
        _product_num - top <n> products to train on, base on their frequency
        _glove - currently loaded GloVe model
        _distance_buffer - newly trained distance vector
    """

    def __init__(self, product_num):
        """
        Create new recommender.

        :param product_num: number of top items to train on, base on their frequency.
        """
        self._product_num = product_num
        self._glove = None
        self.distance = None
        self._distance_buffer = None
        self.version = 1

    def create_new_model(self, data_source, name=None):
        """
        Create new model with data from <data_source> and setup its logger.

        :param data_source: DB manager or raw list with data
        :param name: Name of the model, if None model creation date will be assigned
        """
        if isinstance(data_source, list):
            data = data_source
        else:
            pass  # TODO get form db

        self._glove = GloVe(data,
                            learning_rate=LEARNING_RATE,
                            x_max=X_MAX,
                            alpha=ALPHA,
                            vector_size=V_SIZE,
                            iterations=ITERATIONS,
                            )

        if name is None:
            name = datetime.now().strftime('GloVe[%Y-%m-%d]')
        self._glove.setup_logger(name)

    def sync_version_with_db(self):
        """
        Synchronize model version with last saved in db
        """
        pass

    def train_model(self):
        """
        Train currently loaded model and create distance vector. New distance vector is saved into buffer.
        """
        self._glove.build_vocab(self._product_num)
        self._glove.build_id2word()

        space = self._glove.fit()
        self._distance_buffer = pdist(space)

    def update_distances(self):
        """
        Push reformatted distance vector from buffer into distance attribute. Buffer is set to None after this
        operation.
        """
        self.distance = self._reformat_vector(self._distance_buffer)
        self.version += 1
        self._distance_buffer = None

    def _reformat_vector(self, vector):
        """
        Reformat distance vector into product : distances dictionary
        """
        formatted = {}
        id2word = self._glove.id2word
        hi = 0

        for i in range(self._product_num):
            name = id2word[i]

            lo = hi
            hi = lo + self._product_num - (i + 1)
            dists = vector[lo:hi]

            formatted[name] = list(dists)

        return formatted

    def create_test_model(self):
        """
        Create test model
        """
        data = [
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
        shuffle(data)
        self.create_new_model(data)
