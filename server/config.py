from os import environ

db = {
    'url': environ.get('MONGODB_CONNECTION_URL', None),
    'local_url': 'mongodb://localhost:27017/ShoppingListDb'
}

app = {
    'name': 'ShoppingListApi',
    'debug': False
}

endpoints = {
    'model': '/model/',
    'version': '/model/version'
}

model = {
    'product_num': 6,
    'learning_rate': 0.05,
    'x_max': 100,
    'alpha': 0.75,
    'v_size': 100,
    'iterations': 25,
    'def_version': 1,
    'train_week_day': 'mon',
    'train_hour': 1
}
