from constants import IMAGE_FOLDER
import cv2
import uuid
import os

KEY_1 = ord('1')
KEY_9 = ord('9')
KEY_A = ord('a')
KEY_Z = ord('z')

if not os.path.isdir(IMAGE_FOLDER):
    os.mkdir(IMAGE_FOLDER)

cap = cv2.VideoCapture(0)
cap.set(cv2.CAP_PROP_FRAME_WIDTH, 1280)
cap.set(cv2.CAP_PROP_FRAME_HEIGHT, 720)

while True:
    ret, frame = cap.read()
    cv2.imshow('webcam', frame)

    key = cv2.waitKey(1)
    if key == 27: 
        break
    elif KEY_1 <= key <= KEY_9 or KEY_A <= key <= KEY_Z:
        char = chr(key & 0xFF)
        filename = f'{char}_{uuid.uuid4()}.jpg'
        filepath = os.path.join(IMAGE_FOLDER, filename)
        cv2.imwrite(filepath, frame)
    else: 
        print(key)

cap.release()
cv2.destroyAllWindows()