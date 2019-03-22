from flask import Flask

app = Flask("It's alive!")


@app.route('/')
def hello_world():
    return 'Greetings'


if __name__ == '__main__':
    app.run()
