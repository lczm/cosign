import requests
URL = 'http://127.0.0.1:5000'
r = requests.post(
    URL + '/register', 
    json={
        'email': 'q@q.com', 
        })

print(r.text)