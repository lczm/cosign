import requests

# r = requests.post(
#     URL + '/register', 
#     json={
#         'email': 'q@q.com', 
#         'password': 'qweiop',
#         'username': 'hehehe'
#         })

def test_learn():
    # URL = 'http://127.0.0.1:5000'
    URL = 'http://35.229.247.145:5000'

    # r = requests.post(
    #     URL + '/register', 
    #     json={
    #         'email': 'q@q.com', 
    #         'password': 'qweiop',
    #         'username': 'hehehe'
    #         })

    r = requests.post(
        URL + '/learn',
        json={
            'email': 'q@q.com',
            'password': 'qweiop',
            'sign_id': 1
        }
    )
    print(r.text)
    print(r.status_code)

def test_register():
    # URL = 'http://127.0.0.1:5000'
    URL = 'http://35.229.247.145:5000'

    r = requests.post(
        URL + '/register', 
        json={
            'email': 'cosign@gmail.com', 
            'password': 'password123',
            'username': 'cosign-admin'
            })
    print(r.text)
    print(r.status_code)

def test_login():
    URL = 'http://35.229.247.145:5000'

    r = requests.post(
        URL + '/login', 
        json={
            'email': 'cosign@gmail.com', 
            'password': 'password123',
            })
    print(r.text)
    print(r.status_code)

test_register()
test_login()
