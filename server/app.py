from flask import Flask, jsonify, request, redirect, url_for
from flask_restful import Resource, Api, reqparse
from flask_pymongo import PyMongo
from bson.json_util import dumps
from os import environ
from recomendation import Recommender

app = Flask("ShoppingListApi")
api = Api(app)
# app.config["MONGO_URI"] = "mongodb://localhost:27017/ShoppingListDb"

# app.config["MONGO_URI"] = environ.get('MONGODB_CONNECTION_URL', None)
# mongo = PyMongo(app)

# Test model
rec = Recommender(6)
rec.create_test_model()
rec.train_model()
rec.update_distances()

parser = reqparse.RequestParser()
parser.add_argument('products', action='append')
parser.add_argument('userID', type=int)


class Dists(Resource):
    def get(self):
        return rec.distance


api.add_resource(Dists, '/model/')


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


@app.route('/')
def hello_world():
    return 'Greetings'


if __name__ == '__main__':
    app.run(debug=False)
    # host=0.0.0.0 if accessing server from outside localhost but inside local network
    # host=10.0.2.2 for Android AVD
