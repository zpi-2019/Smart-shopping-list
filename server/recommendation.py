from datetime import datetime, timedelta
from random import shuffle

from scipy.spatial.distance import pdist

import config as cfg
from glove import GloVe


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
        self.version = cfg.model['def_version']

    def create_new_model(self, data_source, name=None):
        """
        Create new model with data from <data_source> and setup its logger.

        :param data_source: DB manager or raw list with data
        :param name: Name of the model, if None model creation date will be assigned
        """
        if isinstance(data_source, list):
            data = data_source
        else:
            data = self._pull_data(data_source, cfg.model['train_period_days'])

        self._glove = GloVe(data,
                            learning_rate=cfg.model['learning_rate'],
                            x_max=cfg.model['x_max'],
                            alpha=cfg.model['alpha'],
                            vector_size=cfg.model['v_size'],
                            iterations=cfg.model['iterations'],
                            )

        if name is None:
            name = datetime.now().strftime('GloVe[%Y-%m-%d]')
        self._glove.setup_logger(name)

    def _pull_data(self, db, days):
        c_date = datetime.now()
        delta = timedelta(days=days)
        date = c_date - delta

        data = db.archive.find({'date': {'$gt': date}}, {'_id': 0, 'list': 1})

        return [record['list'] for record in data]

    def sync_with_db(self, db):
        """
        Synchronize model and version with last saved in db
        """
        cursor = db.models.find({}, {'_id': 0}).sort('version', -1).limit(1)
        if cursor.count():
            model = cursor.next()

            self.version = model['version']
            self.distance = model['model']

    def _save_to_db(self, db):
        """
        Save current model into db
        """
        model = {
            'version': self.version,
            'model': self.distance
        }

        db.models.insert(model)

    def _train_model(self):
        """
        Train currently loaded model and create distance vector. New distance vector is saved into buffer.
        """
        self._glove.build_vocab(self._product_num)
        self._glove.build_id2word()

        space = self._glove.fit()
        self._distance_buffer = pdist(space)

    def _update_distances(self):
        """
        Push reformatted distance vector from buffer into distance attribute. Buffer is set to None after this
        operation.
        """
        self.distance = self._reformat_vector(self._distance_buffer)
        self.version += 1
        self._distance_buffer = None

    def start_training_cycle(self, db, scheduler):
        scheduler.add_job(id='training_job', func=self._training_job, trigger='cron',
                          day_of_week=cfg.model['train_week_day'], hour=cfg.model['train_hour'], args=[db])

    def _training_job(self, db):
        self.create_new_model(db)
        self._train_model()
        self._update_distances()
        self._save_to_db(db)

    def _reformat_vector(self, vector):
        """
        Reformat distance vector into product : distances dictionary
        """
        formatted = {}
        id2word = self._glove.id2word
        hi = 0

        for i, name in id2word.items():
            lo = hi
            hi = lo + len(id2word) - (i + 1)
            dists = vector[lo:hi]

            formatted[name] = list(dists)

        return formatted

    def create_test_model(self):
        """
        Create test model
        """
        data = cfg.model['test_data']
        shuffle(data)
        self.create_new_model(data)
