import os
import json
import pickle
from pprint import pprint

DATASET_JSON = 'dataset-json'

key_dict = {}

json_to_key = lambda json_name: (json_name[:-15])
def json_to_dict(json_name):
    with open(os.path.join(DATASET_JSON, json_name)) as file:
        return json.load(file)
    return -1

for json_name in os.listdir('./dataset-json'):
    key_dict[json_to_key(json_name)] = json_to_dict(json_name)

# # Write to pickle
with open('./keypoints', 'wb') as file:
    pickle.dump(key_dict, file)
