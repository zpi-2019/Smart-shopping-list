from flask_restful import Resource, reqparse
from datetime import datetime


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
        self.parser.add_argument('date', type=lambda x: datetime.strptime(x, '%d.%m.%Y'), location='json')
        self.parser.add_argument('list', type=list, location='json')

    def post(self):
        data = self.parser.parse_args()
        self.db.lists.insert_one({'date': data['date'], 'items': data['list']})
        return 0
