from datetime import datetime

from scipy.spatial.distance import pdist

from glove import GloVe

LEARNING_RATE = 0.05
X_MAX = 100
ALPHA = 0.75
V_SIZE = 100
ITERATIONS = 25


class Recommender:
    """
    Recommender used for training GloVe models on shipping lists


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

    def create_new_model(self, data_source, name=None):
        """
        Create new model with data from <data_source> and setup its logger.

        :param data_source: DB manager or raw list with data
        :param name: Name of the model, if None model creation date will be assigned
        """
        data = data_source  # TODO get form db

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
        Push distance vector from buffer into distance attribute. Buffer is set to None after this operation.
        """
        self.distance = self._distance_buffer
        self._distance_buffer = None
