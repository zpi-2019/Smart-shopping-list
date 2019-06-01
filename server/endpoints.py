from flask_restful import Resource, reqparse
from datetime import datetime
from bson import json_util
import json
from flask import redirect, url_for
import firebase_admin as fb
import config as cfg
import datetime


class Dists(Resource):
    def __init__(self, rec):
        self.rec = rec

    def get(self):
        return self.rec.distance


class Version(Resource):
    def __init__(self, rec):
        self.rec = rec

    def get(self):
        return self.rec.version


class ShoppingList(Resource):
    def __init__(self, db):
        self.db = db
        self.parser = reqparse.RequestParser()
        #self.parser.add_argument('mod_date', type=lambda x: datetime.strptime(x, '%d.%m.%Y'), location='json')
        self.parser.add_argument('userToken', type=str, location='json')
        self.parser.add_argument('listId', type=int, location='json')
        self.parser.add_argument('items', type=list, location='json')

    def get(self, userToken=None, listId=None):
        records = []
        if userToken:
            if listId:
                shopping_lists = self.db.lists.find({'userId' : userToken, 'listId' : listId})
            else:
                shopping_lists = self.db.lists.find({'userId' : userToken})
        else:
            shopping_lists = self.db.lists.find()
        for record in shopping_lists:
            records.append(record)
        return json.loads(json.dumps(records, default=json_util.default))  # prevents json structure from messing up

    def put(self, userToken, listId):
        data = self.parser.parse_args()
        self.db.lists.update_one({'userId' : userToken, 'listId' : listId}, {'$set' : {'items' : data['items'], 'mod_date': datetime.datetime.now()}})
        #return redirect(url_for('list', userID=userToken, listId=listId))

    def post(self):
        data = self.parser.parse_args()

        # TODO enable authentication once implemented in app
        firebase = fb.initialize_app(cfg.firebase_config)
        auth = firebase.auth()
        decoded_token = auth.verify_id_token(data['userToken'])
        uid = decoded_token['uid']

        self.db.lists.insert({'userId' : uid, 'mod_date': datetime.datetime.now(), 'items': data['items'], 'listId': data['listId']})
        #return redirect(url_for('list'))

    def delete(self, userToken, listId):
        self.db.lists.remove({'userId': userToken, 'listId': listId})
