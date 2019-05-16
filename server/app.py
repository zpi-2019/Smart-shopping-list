from flask import Flask, jsonify, request, redirect, url_for
from flask_restful import Resource, Api, reqparse
from flask_pymongo import PyMongo
from bson.json_util import dumps
from os import environ

app = Flask("It's alive!")
api = Api(app)
# app.config["MONGO_URI"] = "mongodb://localhost:27017/ShoppingListDb"
app.config["MONGO_URI"] = environ.get('MONGODB_CONNECTION_URL', None)
mongo = PyMongo(app)

parser = reqparse.RequestParser()
parser.add_argument('products', action='append')
parser.add_argument('userID', type=int)


class ApiServer(Resource):
    def get(self):
        data = mongo.db.shopping_lists.find_one({"_id": 1})
        return data


class ShoppingList(Resource):
    def get(self, userID=None):
        if userID:
            shopping_lists = mongo.db.List.find({'userID': userID})
        else:
            shopping_lists = mongo.db.List.find()
        return dumps(shopping_lists)

    def put(self, userID):  # TODO replace userID with unique attribute (possibly ObjectID)
        args = parser.parse_args()
        mongo.db.List.update_one({'userID': userID}, {'$set': {'products': args['products']}})
        return redirect(url_for('userID', userID=userID))

    def post(self):
        args = parser.parse_args()
        mongo.db.List.insert(args)
        return redirect(url_for('shopping_lists'))

    def delete(self, userID):  # TODO replace userID with unique attribute (possibly ObjectID)
        mongo.db.List.remove({'userID': userID})


api.add_resource(ApiServer, '/api/')
api.add_resource(ShoppingList, '/shopping_lists/', endpoint='shopping_lists')
api.add_resource(ShoppingList, '/shopping_lists/<int:userID>/', endpoint='userID')


@app.route('/')
def hello_world():
    return 'Greetings'


if __name__ == '__main__':
    app.run(debug=True)
    # host=0.0.0.0 if accessing server from outside localhost but inside local network
    # host=10.0.2.2 for Android AVD
