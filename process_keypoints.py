from constants import KEYPOINT_FOLDER
import numpy as np
import os
import json
import pickle

def rel_transform_inplace(hand_keypoint):
    hand_keypoint[0::3] -= hand_keypoint[0]
    hand_keypoint[1::3] -= hand_keypoint[1]

def process_hand_keypoints(keypoints):
    left_keypoints = np.array(keypoints['hand_left_keypoints_2d'])
    right_keypoints = np.array(keypoints['hand_right_keypoints_2d'])
    rel_transform_inplace(left_keypoints)
    rel_transform_inplace(right_keypoints)
    return left_keypoints, right_keypoints

if __name__ == "__main__":
    file_map = {}
    for filename in os.listdir(KEYPOINT_FOLDER):
        char = filename.split('_')[0]
        if char not in file_map:
            file_map[char] = []
        file_map[char].append(filename)

    X_left = []
    X_right = []
    X = []
    y = []
    gloss_map = []
    for index, (char, filenames) in enumerate(file_map.items()):
        gloss_map.append(char)
        _X = []
        _X_left = []
        _X_right = []
        for filename in filenames:
            filepath = os.path.join(KEYPOINT_FOLDER, filename)
            with open(filepath) as file: data = json.load(file)
            
            keypoints = data['people'][0]
            left_keypoints, right_keypoints = process_hand_keypoints(keypoints)
            _X_left.append(left_keypoints)
            _X_right.append(right_keypoints)
            _X.append(np.concatenate((left_keypoints, right_keypoints)))
        _y = np.full(len(_X), index)
        y.append(_y)
        X.append(_X)
        X_left.append(_X_left)
        X_right.append(_X_right)
    X = np.concatenate(X, axis=0)
    X_left = np.concatenate(X_left, axis=0)
    X_right = np.concatenate(X_right, axis=0)
    y = np.concatenate(y)

    np.save('X.npy', X)
    np.save('X_left.npy', X_left)
    np.save('X_right.npy', X_right)
    np.save('y.npy', y)
    with open('gloss_map', 'wb') as file:
        pickle.dump(gloss_map, file)