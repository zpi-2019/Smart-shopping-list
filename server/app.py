from os import environ

from bson.json_util import dumps
from flask import Flask, redirect, url_for
from flask_pymongo import PyMongo
from flask_restful import Api, reqparse

from endpoints import Dists, Version
from recommendation import Recommender


class RESTApp(Flask):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)

        self._api = None
        self._db = None
        self.recommender = None

    def _setup_rest_api(self):
        self._api = Api(self)

    def _setup_db(self):
        self.config["MONGO_URI"] = environ.get('MONGODB_CONNECTION_URL', None)  # TODO
        self._db = PyMongo(self).db

    def _setup_recommendations(self):
        self.recommender = Recommender(6)
        self.recommender.sync_with_db(self._db)
        # TODO

    def _setup_endpoints(self):
        self._api.add_resource(Dists, '/model/', resource_class_kwargs={'rec': self.recommender})
        self._api.add_resource(Version, '/model/version', resource_class_kwargs={'rec': self.recommender})

    def setup(self):
        self._setup_db()
        self._setup_rest_api()
        self._setup_recommendations()
        self._setup_endpoints()


app = RESTApp("ShoppingListApi")
app.setup()

# "mongodb://localhost:27017/ShoppingListDb"
# parser = reqparse.RequestParser()
# parser.add_argument('products', action='append')
# parser.add_argument('userID', type=int)
#
#
# class ShoppingList(Resource):
#     def get(self, userID=None):
#         if userID:
#             shopping_lists = mongo.db.List.find({'userID': userID})
#         else:
#             shopping_lists = mongo.db.List.find()
#         return dumps(shopping_lists)
#
#     def put(self, userID):  # TODO replace userID with unique attribute (possibly ObjectID)
#         args = parser.parse_args()
#         mongo.db.List.update_one({'userID': userID}, {'$set': {'products': args['products']}})
#         return redirect(url_for('userID', userID=userID))
#
#     def post(self):
#         args = parser.parse_args()
#         mongo.db.List.insert(args)
#         return redirect(url_for('shopping_lists'))
#
#     def delete(self, userID):  # TODO replace userID with unique attribute (possibly ObjectID)
#         mongo.db.List.remove({'userID': userID})
#
#
# api.add_resource(ShoppingList, '/shopping_lists/', endpoint='shopping_lists')
# api.add_resource(ShoppingList, '/shopping_lists/<int:userID>/', endpoint='userID')


@RESTApp.route(app, '/')
def hello_world():
    return 'Greetings'


if __name__ == '__main__':
    app.run(debug=False)
    # host=0.0.0.0 if accessing server from outside localhost but inside local network
    # host=10.0.2.2 for Android AVD
