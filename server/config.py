from os import environ

db = {
    'url': 'mongodb://localhost:27017/ShoppingListDb',  #environ.get('MONGODB_CONNECTION_URL', None),
    'local_url': 'mongodb://localhost:27017/ShoppingListDb'
}

firebase_config = {
    "apiKey": environ.get('FIREBASE_KEY', None),
    "authDomain": environ.get('FIREBASE_AUTHDOMAIN', None),
    "databaseURL": environ.get('FIREBASE_DATABASEURL', None),
    "projectId": environ.get('FIREBASE_PROJECTID', None),
    "storageBucket": environ.get('FIREBASE_STORAGEBUCKET', None),
    "messagingSenderId": environ.get('FIREBASE_MESSAGINGSENDERID', None),
    "appId": environ.get('FIREBASE_APPID', None)
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
    'list': '/lists/',
    'list_by_userToken' : '/lists/<string:userToken>/',
    'list_by_listId' : '/lists/<string:userToken>/<int:listId>/'
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
