import pickle
import os
import subprocess
import json
import time
import numpy as np
import tensorflow as tf
from tensorflow.keras.models import load_model

OPENPOSE_WD = r'C:\Users\yanhw\Desktop\openpose-1.5.1-binaries-win64-gpu-python-flir-3d_recommended\openpose'
OPENPOSE_EXE = r'C:\Users\yanhw\Desktop\openpose-1.5.1-binaries-win64-gpu-python-flir-3d_recommended\openpose\bin\OpenPoseDemo.exe'
IO_FOLDER = os.path.abspath('inout')

#graph = tf.get_default_graph()
model = load_model('model.h5', custom_objects={"GlorotUniform": tf.keras.initializers.glorot_uniform})

with open('gloss_map', 'rb') as file:
    gloss_map = pickle.load(file)

def rel_transform_inplace(hand_keypoint):
    hand_keypoint[0::3] -= hand_keypoint[0]
    hand_keypoint[1::3] -= hand_keypoint[1]

if not os.path.isdir(IO_FOLDER): os.mkdir(IO_FOLDER)

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
    frame = people[0]
    left_keypoint = np.array(frame['hand_left_keypoints_2d'])
    right_keypoint = np.array(frame['hand_right_keypoints_2d'])
    rel_transform_inplace(left_keypoint)
    rel_transform_inplace(right_keypoint)
    X = np.concatenate((left_keypoint, right_keypoint)).reshape(1, -1)

    print('!!!!!!!!!!!!!!!', X.shape)
    #with graph.as_default():
    y = model.predict(X)
    y1 = y[0]

    max_index = np.argmax(y1)
    max_acc = y1[max_index]
    gloss = gloss_map[max_index]

    print(gloss)
    print(max_acc)

    # Prepare for next
    os.remove(io_json)
    count += 1