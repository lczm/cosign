from process_keypoints import rel_transform_inplace, process_hand_keypoints
import pickle
import json
import subprocess
import os
import time
import requests

### Constants ###
OPENPOSE_WD = r'C:\Users\yanhw\Desktop\openpose-1.5.1-binaries-win64-gpu-python-flir-3d_recommended\openpose'
OPENPOSE_EXE = r'C:\Users\yanhw\Desktop\openpose-1.5.1-binaries-win64-gpu-python-flir-3d_recommended\openpose\bin\OpenPoseDemo.exe'
IO_FOLDER = os.path.abspath('inout')

### Load Models
with open('rfc', 'rb') as file:         model = pickle.load(file)
with open('gloss_map', 'rb') as file:   gloss_map = pickle.load(file)

### Webcam
cap = cv2.VideoCapture(0)
cap.set(cv2.CAP_PROP_FRAME_WIDTH, 1280) # Comment out if needed
cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 720)

while True:
    ret, image = cap.read()
    cv2.imwrite('temp.jpg', image)

    # TODO: Communicate with Server
    r = requests.get('', json={'image': open('temp.jpg', 'rb')})
    data = r.json()
    left_keypoints = data['left']
    right_keypoints = data['right']
    # These preprocessing methods are to be implemented by the server
    # def rel_transform_inplace(hand_keypoint):
    #     hand_keypoint[0::3] -= hand_keypoint[0]
    #     hand_keypoint[1::3] -= hand_keypoint[1]

    # def process_hand_keypoints(keypoints):
    #     left_keypoints = np.array(keypoints['hand_left_keypoints_2d'])
    #     right_keypoints = np.array(keypoints['hand_right_keypoints_2d'])
    #     rel_transform_inplace(left_keypoints)
    #     rel_transform_inplace(right_keypoints)
    #     return left_keypoints, right_keypoints

    # Predict
    y = model.predict([right_keypoints])
    gloss = gloss_map[y[0]]
    print(gloss)
    height, width, _ = image.shape
    image = cv2.putText(image, gloss, (0, height), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 0, 0), 2, cv2.LINE_AA)

    # Show
    cv2.imshow('webcam', image)
    # Quit
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break