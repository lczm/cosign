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
    gloss = data['answer']
    print(gloss)
    height, width, _ = image.shape
    image = cv2.putText(image, gloss, (0, height), cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 0, 0), 2, cv2.LINE_AA)

    # Show
    cv2.imshow('webcam', image)
    # Quit
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break