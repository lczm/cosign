import io
import os
import sys
import cv2
import math
import time
import pickle

# Import OpenPose
dir_path = os.path.dirname(os.path.realpath(__file__))
# Point the variables to the correct folder
sys.path.append("../../python")

import numpy as np
from openpose import pyopenpose as op
from PIL import Image
from flask import Flask, request, jsonify
from pprint import pprint

import tensorflow as tf

detection_graph = tf.Graph()
# sys.path.append("..")

MODEL_NAME = 'hand_inference_graph'
# Path to frozen detection graph. This is the actual model that is used for the object detection.
PATH_TO_CKPT = '../hand_inference_graph/frozen_inference_graph.pb'
# List of the strings that is used to add correct label for each box.
PATH_TO_LABELS = '../hand_inference_graph/hand_label_map.pbtxt'

# Load a frozen infrerence graph into memory
def load_inference_graph():
    # load frozen tensorflow model into memory
    print("> ====== loading HAND frozen graph into memory")
    # gpu_options = tf.GPUOptions(per_process_gpu_memory_fraction=0.33)
    gpu_options = tf.GPUOptions(per_process_gpu_memory_fraction=1)
    detection_graph = tf.Graph()
    with detection_graph.as_default():
        od_graph_def = tf.GraphDef()
        with tf.gfile.GFile(PATH_TO_CKPT, 'rb') as fid:
            serialized_graph = fid.read()
            od_graph_def.ParseFromString(serialized_graph)
            tf.import_graph_def(od_graph_def, name='')
        sess = tf.Session(graph=detection_graph, config=tf.ConfigProto(gpu_options=gpu_options))
        # sess = tf.Session(graph=detection_graph)
    print(">  ====== Hand Inference graph loaded.")
    return detection_graph, sess

# Actual detection .. generate scores and bounding boxes given an image
def detect_objects(image_np, detection_graph, sess):
    # Definite input and output Tensors for detection_graph
    image_tensor = detection_graph.get_tensor_by_name('image_tensor:0')
    # Each box represents a part of the image where a particular object was detected
    detection_boxes = detection_graph.get_tensor_by_name('detection_boxes:0')
    # Each score represent how level of confidence for each of the objects
    # Score is shown on the result image, together with the class label
    detection_scores = detection_graph.get_tensor_by_name('detection_scores:0')
    detection_classes = detection_graph.get_tensor_by_name('detection_classes:0')
    num_detections = detection_graph.get_tensor_by_name('num_detections:0')

    image_np_expanded = np.expand_dims(image_np, axis=0)
    (boxes, scores, classes, num) = sess.run(
            [detection_boxes, detection_scores,
                detection_classes, num_detections],
            feed_dict={image_tensor: image_np_expanded})

    return np.squeeze(boxes), np.squeeze(scores)

def getbox(score_thresh, scores, boxes, im_width, im_height, image_np):
    x = []
    for i in range(2):
        if (scores[i] > score_thresh):
            print('GOOD : ', scores[i])
            (left, right, top, bottom) = (boxes[i][1] * im_width, boxes[i][3] * im_width,
                                            boxes[i][0] * im_height, boxes[i][2] * im_height)
            p1 = (int(left), int(top))
            p2 = (int(right), int(bottom))

            x.append([p1, p2])
    return x

def rel_transform_inplace(hand_keypoint):
    hand_keypoint[0::3] -= hand_keypoint[0]
    hand_keypoint[1::3] -= hand_keypoint[1]

def rel_scale_inplace(hand_keypoint, width, height):
    hand_keypoint[0::3] /= width
    hand_keypoint[1::3] /= height

params = dict()
params["model_folder"] = "../../../models/"
# params["hand_net_resolution"] = "480x480"
params["hand"] = True
params["hand_detector"] = 2
params["body"] = 0
params["disable_blending"] = True

opWrapper = op.WrapperPython()
opWrapper.configure(params)
opWrapper.start()

app = Flask(__name__)
detection_graph, sess = load_inference_graph()

# Loading pickle models
# with open('/home/cat/cosign/backend/rfc', 'rb') as file:
with open('./rfc', 'rb') as file:
    model = pickle.load(file)

# with open('/home/cat/cosign/backend/gloss_map', 'rb') as file:
with open('./gloss_map', 'rb') as file:
    gloss_map = pickle.load(file)

@app.route('/')
def hello():
    return 'hello'

@app.route('/image', methods=['GET', 'POST'])
def image():
    print("--- Predicting ---")
    start = time.time()
    
    image = request.files["file"]
    pil_image = Image.open(io.BytesIO(image.read()))
    # TODO : Only for mobile

    # pil_image = pil_image.transpose(Image.FLIP_LEFT_RIGHT)
    pil_image = pil_image.transpose(Image.ROTATE_90)
    # pil_image = pil_image.resize((480, 480), Image.ANTIALIAS)

    pil_image.save("mobileImage.png")

    width = pil_image.width
    height = pil_image.height

    pil_image_np = np.array(pil_image)
    relative_boxes, scores = detect_objects(pil_image_np, detection_graph, sess)
    # box_relative2absolute = lambda box: (box[1] * width, box[3] * width, box[0] * height, box[2] * height)
    hand_boxes = getbox(0.3, scores, relative_boxes, width, height, pil_image_np)


    if len(hand_boxes) < 1:
        print('Bad image', len(hand_boxes))
        return jsonify({'error': 'Bad image'})
    
    print("--- Number of hands ", len(hand_boxes))

    # Get the parameters for the bounding box
    # square_length = min(pil_image.width, pil_image.height)
    # if pil_image.width >= pil_image.height:
    #     x = (pil_image.width - pil_image.height) / 2
    #     y = 0
    # elif pil_image.height >= pil_image.width:
    #     x = 0
    #     y = (pil_image.height - pil_image.width) / 2

    left_scale = 1.3
    right_scale = 1.3

    left_x = 0
    left_y = 0
    left_width = 0
    left_height = 0
    left_square_length = 0

    right_x = 0
    right_y = 0
    right_width = 0
    right_height = 0
    right_square_length = 0

    if len(hand_boxes) >= 1:
        left_x = hand_boxes[0][0][0] - (hand_boxes[0][0][0] * (left_scale - 1) / 2)
        left_y = hand_boxes[0][0][1] - (hand_boxes[0][0][1] * (left_scale - 1) / 2)
        left_width = math.ceil((hand_boxes[0][1][0] - hand_boxes[0][0][0]))
        left_height = math.ceil((hand_boxes[0][1][1] - hand_boxes[0][0][1]))
        left_square_length = max(left_width, left_height) * left_scale * right_scale

        if left_x + left_square_length > width:
            left_square_length = width - left_x
        if left_y + left_square_length > height:
            left_square_length = height - left_y

    if len(hand_boxes) == 2:
        right_x = hand_boxes[1][0][0] - (hand_boxes[1][0][0] * (right_scale - 1) / 2)
        right_y = hand_boxes[1][0][1] - (hand_boxes[1][0][1] * (right_scale - 1) / 2)
        right_width = math.ceil((hand_boxes[1][1][0] - hand_boxes[1][0][0]))
        right_height = math.ceil((hand_boxes[1][1][1] - hand_boxes[1][0][1]))
        right_square_length = max(right_width, right_height) * right_scale * right_scale

        if right_x + right_square_length > width:
            right_square_length = width - right_x
        if right_y + right_square_length > height:
            right_square_length = height - right_y

    # print(right_x, right_y, right_square_length)
    # print(left_x, left_y, left_square_length)

    # The bounding box must be a square, hence square_length
    handRectangle = [[
        # op.Rectangle(x, y, square_length, square_length),
        # op.Rectangle(x, y, square_length, square_length),

	# Left then Right, Note : The left of the person in the picture.
	# So the Right then Left when viewing
        # Note : Adding of x and y is when the iamge has to be re-sized
        # op.Rectangle(250 + x, 350 + y, 80, 80),
        # op.Rectangle(hand_boxes[0][0][0] - 20, hand_boxes[0][0][1], math.ceil(hand_boxes[0][1][0] - hand_boxes[0][0][0] + 30), math.ceil(hand_boxes[0][1][0] - hand_boxes[0][0][0] + 30)),
        # op.Rectangle(130 + x, 110 + y, 80, 80),
        # op.Rectangle(hand_boxes[0][1][0], hand_boxes[0][1][1], math.ceil(hand_boxes[0][1][0] - hand_boxes[0][1][0]), math.ceil(hand_boxes[0][1][0] - hand_boxes[0][0][0])),

        # op.Rectangle(0., 0., 0., 0.),
        op.Rectangle(right_x, right_y, right_square_length, right_square_length),
        op.Rectangle(left_x, left_y, left_square_length, left_square_length),
        # op.Rectangle(hand_boxes[0][0][0] - 20, hand_boxes[0][0][1], math.ceil(hand_boxes[0][1][0] - hand_boxes[0][0][0] + 30), math.ceil(hand_boxes[0][1][0] - hand_boxes[0][0][0] + 30)),
    ]]

    # Convert pillow image to opencv
    opencvImage = cv2.cvtColor(np.array(pil_image), cv2.COLOR_RGB2BGR)
    print("--- Time taken; Preprocess Image", str(time.time() - start))

    datum = op.Datum()
    datum.cvInputData = opencvImage
    datum.handRectangles = handRectangle
    opWrapper.emplaceAndPop([datum])

    x_left = datum.handKeypoints[0]
    x_right = datum.handKeypoints[1]

    # Copy x_left, as they are being returned
    # left = np.array(x_left, copy=True, dtype=float)
    left = np.array(x_left, copy=True)
    # right = np.array(x_right, copy=True, dtype=float)
    right = np.array(x_right, copy=True)

    left = left.reshape(-1)
    right = right.reshape(-1)

    rel_transform_inplace(left)
    rel_transform_inplace(right)

    # rel_scale_inplace(left, width, height)
    # rel_scale_inplace(right, width, height)

    y = model.predict([right])
    gloss = gloss_map[y[0]]

    print("--- Time taken; Final", str(time.time() - start), "Answer : ", gloss)
    return jsonify({'answer': gloss, 'left' : x_left.tolist(), 'right': x_right.tolist()})

    # Debugging purposes
    return jsonify({'left':datum.handKeypoints[0].tolist(), 'right':datum.handKeypoints[1].tolist()})

if __name__ == '__main__':
    print("Starting the Flask Server")
    app.run(host='0.0.0.0', port=5001)
    # app.run(host='0.0.0.0', port=5000)
    # app.run(host='0.0.0.0', port=5000, debug=True)
