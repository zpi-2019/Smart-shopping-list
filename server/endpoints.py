from flask_restful import Resource, reqparse
from datetime import datetime
from bson import json_util
import json
from flask import redirect, url_for
import config as cfg
import datetime
from firebase_admin import auth


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
        self.parser.add_argument('userToken', type=str, location='json', store_missing=False)
        self.parser.add_argument('listId', type=int, location='json', store_missing=False)
        self.parser.add_argument('items', type=list, location='json')

    def get(self):
        data = self.parser.parse_args()
        records = []
        if 'userToken' in data:
            decoded_token = auth.verify_id_token(data['userToken'])
            uid = decoded_token['uid']
            if 'listId' in data:
                listId = data['listId']
                shopping_lists = self.db.lists.find({'userId' : uid, 'listId' : listId}, {'_id': 0})
            else:
                shopping_lists = self.db.lists.find({'userId' : uid}, {'_id': 0})
        else:
            shopping_lists = self.db.lists.find({}, {'_id': 0})
        for record in shopping_lists:
            records.append(record)
        return json.loads(json.dumps(records, default=json_util.default))  # prevents json structure from messing up

    def post(self):
        data = self.parser.parse_args()
        decoded_token = auth.verify_id_token(data['userToken'])
        uid = decoded_token['uid']

        shopping_list = self.db.lists.find_one({'userId': data['userToken'], 'listId': data['listId']})

        if shopping_list:
            self.db.lists.update_one({'userId': data['userToken'], 'listId': data['listId']}, {
                '$set': {'items': data['items'], 'mod_date': datetime.datetime.now(), 'listVer': shopping_list['listVer'] + 1}})
        else:
            self.db.lists.insert({'userId' : data['userToken'], 'mod_date': datetime.datetime.now(), 'items': data['items'], 'listId': data['listId'], 'listVer': 1})
        #return redirect(url_for('list'))

    def delete(self):
        data = self.parser.parse_args()
        decoded_token = auth.verify_id_token(data['userToken'])
        uid = decoded_token['uid']
        self.db.lists.remove({'userId': uid, 'listId': data['listId']})
