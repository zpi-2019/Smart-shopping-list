from apscheduler.schedulers.background import BackgroundScheduler
from flask import Flask
from flask_pymongo import PyMongo
from flask_restful import Api

import config as cfg
from endpoints import Dists, Version, ShoppingList
from recommendation import Recommender


class RESTApp(Flask):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

        self._api = None
        self._db = None
        self.recommender = None
        self.scheduler = None

    def _setup_rest_api(self):
        self._api = Api(self)

    def _setup_db(self):
        self.config['MONGO_URI'] = cfg.db['url']
        self._db = PyMongo(self).db

    def _setup_scheduler(self):
        self.scheduler = BackgroundScheduler()
        self.scheduler.start()

    def _setup_recommendations(self):
        self.recommender = Recommender(cfg.model['product_num'])
        self.recommender.sync_with_db(self._db)
        # self.recommender.start_training_cycle(self._db)

    def _setup_endpoints(self):
        self._api.add_resource(Dists, cfg.endpoints['model'], resource_class_kwargs={'rec': self.recommender})
        self._api.add_resource(Version, cfg.endpoints['version'], resource_class_kwargs={'rec': self.recommender})
        self._api.add_resource(ShoppingList, cfg.endpoints['list'], resource_class_kwargs={'db': self._db})

    def setup(self):
        self._setup_db()
        self._setup_rest_api()
        # self._setup_scheduler()
        self._setup_recommendations()
        self._setup_endpoints()


app = RESTApp(cfg.app['name'])
app.setup()


@RESTApp.route(app, '/')
def hello_world():
    return 'Greetings'


if __name__ == '__main__':
    app.run(debug=cfg.app['debug'])
