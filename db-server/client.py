import requests
URL = 'http://127.0.0.1:5000'
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