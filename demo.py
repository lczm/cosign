from constants import OPENPOSE_EXE, OPENPOSE_WD, IO_FOLDER
from process_keypoints import rel_transform_inplace, process_hand_keypoints
import pickle
import json
import subprocess
import os
import time

with open('rfc', 'rb') as file:         model = pickle.load(file)
with open('gloss_map', 'rb') as file:   gloss_map = pickle.load(file)

# Start OpenPose
subprocess.Popen([
    OPENPOSE_EXE,
    '--hand',
    '--number_people_max', '1',
    '--write_json', IO_FOLDER], 
    cwd=OPENPOSE_WD)

# Ghost OpenPose Output
count = 0
while True:
    # Find JSON File
    io_json = os.path.join(
        IO_FOLDER, 
        f'{"%012d"%count}_keypoints.json')

    while True:
        if os.path.isfile(io_json):
            break
    time.sleep(0.1)

    # Predict
    with open(io_json) as file:
        data = json.load(file)
    people = data['people']
    if not people: continue
    keypoints = people[0]
    _, right_keypoints = process_hand_keypoints(keypoints)
    y = model.predict([right_keypoints])
    gloss = gloss_map[y[0]]
    print(gloss)

    # Prepare for next
    os.remove(io_json)
    count += 1
