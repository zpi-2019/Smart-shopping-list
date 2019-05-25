from datetime import datetime, timedelta

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

    def _archive_job(self):
        c_date = datetime.now()
        delta = timedelta(days=cfg.app['archive_min_age_days'])
        date = c_date - delta

        data = self._db.lists.find({'mod_date': {'$lt': date}}, {'_id': 0})

        # TODO clean data

        self._db.archive.insert_many(data)
        self._db.lists.delete_many({'mod_date': {'$lt': date}})

    def _schedule_jobs(self):
        self.recommender.start_training_cycle(self._db)
        self.scheduler.add_job(self._archive_job, 'cron', day_of_week=cfg.app['archive_week_day'],
                               hour=cfg.app['archive_hour'])

    def _setup_recommendations(self):
        self.recommender = Recommender(cfg.model['product_num'])
        self.recommender.sync_with_db(self._db)

    def _setup_endpoints(self):
        self._api.add_resource(Dists, cfg.endpoints['model'], resource_class_kwargs={'rec': self.recommender})
        self._api.add_resource(Version, cfg.endpoints['version'], resource_class_kwargs={'rec': self.recommender})
        self._api.add_resource(ShoppingList, cfg.endpoints['list'], resource_class_kwargs={'db': self._db})

    def setup(self):
        self._setup_db()
        self._setup_rest_api()
        self._setup_recommendations()
        # self._setup_scheduler()
        # self._schedule_jobs()
        self._setup_endpoints()


app = RESTApp(cfg.app['name'])
app.setup()


@RESTApp.route(app, '/')
def hello_world():
    return 'Greetings'


if __name__ == '__main__':
    app.run(debug=cfg.app['debug'])
