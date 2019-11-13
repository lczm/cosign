import numpy as np
import json

X_left = np.load('X_left.npy')
X_right = np.load('X_right.npy')
X = np.load('X.npy')

def rel_transform_inplace(hand_keypoint):
    hand_keypoint[0::3] -= hand_keypoint[0]
    hand_keypoint[1::3] -= hand_keypoint[1]
    
filepath = 'keypoints/1_1ec91cca-b3b2-470c-afdf-3dfe409b095f_keypoints.json'
with open(filepath) as file: data = json.load(file)

keypoints = data['people'][0]
left_keypoints = np.array(keypoints['hand_left_keypoints_2d'])
right_keypoints = np.array(keypoints['hand_right_keypoints_2d'])
rel_transform_inplace(left_keypoints)
rel_transform_inplace(right_keypoints)
left_right = np.concatenate((left_keypoints, right_keypoints))