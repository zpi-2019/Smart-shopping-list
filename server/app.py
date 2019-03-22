from flask import Flask
from flask_restful import Resource, Api

app = Flask("It's alive!")
api = Api(app)


class ApiServer(Resource):
    def get(self):
        return {'halo?': 'halo!'}


api.add_resource(ApiServer, '/api/')


@app.route('/')
def hello_world():
    return 'Greetings'


if __name__ == '__main__':
    app.run()
