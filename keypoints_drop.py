import pickle
import numpy as np
import matplotlib.pyplot as plt

with open('keypoints2.1', 'rb') as file:
    data = pickle.load(file)

lefts_drop = []
rights_drop = []
frames_total = []
glosses = []

for gloss in data:
    left_drop = 0
    right_drop = 0
    frame_total = 0
    for variant in data[gloss]:
        for session in data[gloss][variant]:
            for frame in data[gloss][variant][session]:
                left = np.array(frame['hand_left_keypoints_2d'])
                right = np.array(frame['hand_right_keypoints_2d'])
                if np.any(left[2::3] < 0.3): left_drop += 1
                if np.any(right[2::3] < 0.3): right_drop += 1
                # left_drop += (left[2::3] < 0.5).sum()
                # right_drop += (right[2::3] < 0.5).sum()
                frame_total += 1
    glosses.append(gloss)
    lefts_drop.append(left_drop)
    rights_drop.append(right_drop)
    frames_total.append(frame_total)

lefts_drop = np.array(lefts_drop)
rights_drop = np.array(rights_drop)
frames_total = np.array(frames_total)

lefts_drop_percent = lefts_drop / frames_total * 100
rights_drop_percent = rights_drop / frames_total * 100

ind = np.arange(len(lefts_drop))
width = 0.35

c = 1.1
fig, ax = plt.subplots()
lefts_bar = ax.bar(ind - width / c, lefts_drop, width / c, label='Left hands')
rights_bar = ax.bar(ind, rights_drop, width / c, label='Right hands')
total_bar = ax.bar(ind + width / c, frames_total, width / c, label='Total coords')

ax.set_xticks(ind)
ax.set_xticklabels(glosses)
ax.legend()

fig.tight_layout()
plt.show()

fig, ax = plt.subplots()
lefts_percent_bar = ax.bar(ind - width / 2, lefts_drop_percent, width, label='Left hands')
rights_percent_bar = ax.bar(ind + width / 2, rights_drop_percent, width, label='Right hands')
ax.set_xticks(ind)
ax.set_xticklabels(glosses)
ax.legend()
fig.tight_layout()
plt.show()