import cv2
import pickle
import os
import subprocess
import json
import numpy as np

OPENPOSE_WD = r'C:\Users\yanhw\Desktop\openpose-1.5.1-binaries-win64-gpu-python-flir-3d_recommended\openpose'
OPENPOSE_EXE = r'C:\Users\yanhw\Desktop\openpose-1.5.1-binaries-win64-gpu-python-flir-3d_recommended\openpose\bin\OpenPoseDemo.exe'
IO_FOLDER = os.path.abspath('inout')
IO_IMG = os.path.join(IO_FOLDER, 'img.jpg')
IO_JSON = os.path.join(IO_FOLDER, 'img_keypoints.json')

with open('clf', 'rb') as file:
    model = pickle.load(file)

with open('gloss_map', 'rb') as file:
    gloss_map = pickle.load(file)

if not os.path.isdir(IO_FOLDER): os.mkdir(IO_FOLDER)

cap = cv2.VideoCapture(0)
while True:
    ret, image = cap.read()
    cv2.imwrite(IO_IMG, image)

    subprocess.run([
        OPENPOSE_EXE, 
        '--hand',
        '--number_people_max', '1',
        '--display', '0',
        '--render_pose', '0',
        '--write_json', IO_FOLDER,
        '--image_dir', IO_FOLDER], 
        cwd=OPENPOSE_WD)

    # Predict
    with open(IO_JSON) as file:
        data = json.load(file)
    people = data['people']
    if not people: continue
    frame = people[0]
    X = [frame['hand_left_keypoints_2d'] + frame['hand_right_keypoints_2d']]
    y = model.predict(X)
    y1 = y[0]
    gloss = gloss_map[y1]

    # Draw
    for x, y, c in np.reshape(X, (-1, 3)):
        image = cv2.circle(image, (int(x),int(y)), 3, (0,0,255), -1)

    height, width, _ = image.shape

    image = cv2.putText(image, gloss, (0, height), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 0, 0), 2, cv2.LINE_AA)

    cv2.imshow('image', image)
    cv2.waitKey(1)
    print(gloss)