from os import environ
import json

db = {
    'url': environ.get('MONGODB_CONNECTION_URL', None),
    'local_url': 'mongodb://localhost:27017/ShoppingListDb'
}

firebase = {
    'cred': json.loads(environ.get('FIREBASE_SETUP_JSON', None))
}

app = {
    'name': 'ShoppingListApi',
    'debug': True,
    'archive_week_day': 'sun',
    'archive_hour': 22,
    'archive_min_age_days': 7
}

endpoints = {
    'model': '/model/',
    'version': '/model/version',
    'list': '/lists/'
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
    'train_hour': 1,
    'train_period_days': 30,
    'test_data': [
        ['owoce', 'szynka'],
        ['owoce', 'szynka'],
        ['owoce', 'szynka'],
        ['owoce', 'szynka'],
        ['owoce', 'szynka'],
        ['owoce', 'szynka'],
        ['owoce', 'szynka'],
        ['owoce', 'szynka'],
        ['woda', 'chleb'],
        ['woda', 'chleb'],
        ['woda', 'chleb'],
        ['woda', 'chleb'],
        ['woda', 'chleb'],
        ['woda', 'chleb'],
        ['woda', 'chleb'],
        ['woda', 'chleb'],
        ['kawa', 'ciastka'],
        ['kawa', 'ciastka'],
        ['kawa', 'ciastka'],
        ['kawa', 'ciastka'],
        ['kawa', 'ciastka'],
        ['kawa', 'ciastka'],
        ['kawa', 'ciastka'],
        ['kawa', 'ciastka'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
        ['szynka', 'woda'],
    ]
}
