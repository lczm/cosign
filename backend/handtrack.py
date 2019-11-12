import os
import cv2
import sys
import datetime
import numpy as np
import tensorflow as tf

import matplotlib.pyplot as plt
import matplotlib.patches as patches
import matplotlib.image as mpimg

from PIL import Image
from pprint import pprint


detection_graph = tf.Graph()
sys.path.append("..")
np.set_printoptions(suppress=True)

MODEL_NAME = 'hand_inference_graph'
# Path to frozen detection graph. This is the actual model that is used for the object detection.
PATH_TO_CKPT = MODEL_NAME + '/frozen_inference_graph.pb'
# List of the strings that is used to add correct label for each box.
PATH_TO_LABELS = os.path.join(MODEL_NAME, 'hand_label_map.pbtxt')

# Load a frozen infrerence graph into memory
def load_inference_graph():
    # load frozen tensorflow model into memory
    print("> ====== loading HAND frozen graph into memory")
    gpu_options = tf.GPUOptions(per_process_gpu_memory_fraction=0.33)
    detection_graph = tf.Graph()
    with detection_graph.as_default():
        od_graph_def = tf.GraphDef()
        with tf.gfile.GFile(PATH_TO_CKPT, 'rb') as fid:
            serialized_graph = fid.read()
            od_graph_def.ParseFromString(serialized_graph)
            tf.import_graph_def(od_graph_def, name='')
        sess = tf.Session(graph=detection_graph, config=tf.ConfigProto(gpu_options=gpu_options))
    print(">  ====== Hand Inference graph loaded.")
    return detection_graph, sess


# Actual detection .. generate scores and bounding boxes given an image
def detect_objects(image_np, detection_graph, sess):
    # Definite input and output Tensors for detection_graph
    image_tensor = detection_graph.get_tensor_by_name('image_tensor:0')
    # Each box represents a part of the image where a particular object was detected.
    detection_boxes = detection_graph.get_tensor_by_name('detection_boxes:0')
    # Each score represent how level of confidence for each of the objects.
    detection_scores = detection_graph.get_tensor_by_name('detection_scores:0')
    detection_classes = detection_graph.get_tensor_by_name('detection_classes:0')
    num_detections = detection_graph.get_tensor_by_name('num_detections:0')

    image_np_expanded = np.expand_dims(image_np, axis=0)

    (boxes, scores, classes, num) = sess.run(
        [detection_boxes, detection_scores,
            detection_classes, num_detections],
        feed_dict={image_tensor: image_np_expanded})

    return np.squeeze(boxes), np.squeeze(scores), np.squeeze(classes)

def getbox(score_thresh, scores, boxes, im_width, im_height, image_np):
    x = []
    for i in range(2):
        if (scores[i] > score_thresh):
            (left, right, top, bottom) = (boxes[i][1] * im_width, boxes[i][3] * im_width,
                                            boxes[i][0] * im_height, boxes[i][2] * im_height)
            p1 = (int(left), int(top))
            p2 = (int(right), int(bottom))
            x.append([p1, p2])
    return x

detection_graph, sess = load_inference_graph()

# Create an image
width = Image.open('./datasetimage2.png').width
height = Image.open('./datasetimage2.png').height
img_np = np.array(Image.open('./datasetimage2.png'))
# img_np = cv2.cvtColor(img_np, cv2.COLOR_BGR2RGB)

relative_boxes, scores, classes = detect_objects(img_np, detection_graph, sess)
box_relative2absolute = lambda box: (box[1] * width, box[3] * width, box[0] * height, box[2] * height)
# hand_boxes = [box_relative2absolute(box)  for box,score in zip(relative_boxes,scores) if score > 0.8]

hand_boxes = getbox(0.8, scores, relative_boxes, width, height, img_np)
print(hand_boxes)
# for i in range(len(relative_boxes)):
#     if scores[i] > 0.8:
#         hand_boxes.append(box_relative2absolute(relative_boxes[i]))

# Show the image
image = np.array(Image.open('datasetimage2.png'))
fig, ax = plt.subplots(1)
ax.imshow(image)

for box in hand_boxes:
    rect = patches.Rectangle((box[0][0], box[0][1]), box[1][0] - box[0][0], box[1][1] - box[0][1], linewidth=1, edgecolor='r', facecolor='none')
    ax.add_patch(rect)

plt.show()

# print('relative_boxes', relative_boxes)
# print('scores', scores)
# print('classes', classes)