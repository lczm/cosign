from constants import OPENPOSE_EXE, OPENPOSE_WD, KEYPOINT_FOLDER, IMAGE_FOLDER
import subprocess
import os

TEMP_FOLDER = os.path.abspath('temp')
if not os.path.isdir(TEMP_FOLDER): os.mkdir(TEMP_FOLDER)

pic_names = set([os.path.splitext(filename)[0] for filename in os.listdir(IMAGE_FOLDER)])
json_names = set([os.path.splitext(filename)[0][:-10] for filename in os.listdir(KEYPOINT_FOLDER)])
new_pic_names = pic_names - json_names

# Move New Pics to Temp Folder
for pic_name in new_pic_names:
    src = os.path.join(IMAGE_FOLDER, f'{pic_name}.jpg')
    dest = os.path.join(TEMP_FOLDER, f'{pic_name}.jpg')
    os.rename(src, dest)

# Openpose
subprocess.run([
    OPENPOSE_EXE,
    '--hand',
    '--number_people_max', '1',
    '--image_dir', TEMP_FOLDER,
    '--write_json', KEYPOINT_FOLDER],
    cwd=OPENPOSE_WD)

# Move back pics
for pic_name in os.listdir(TEMP_FOLDER):
    pic_name = os.path.splitext(pic_name)[0]
    src = os.path.join(TEMP_FOLDER, f'{pic_name}.jpg')
    dest = os.path.join(IMAGE_FOLDER, f'{pic_name}.jpg')
    os.rename(src, dest)

# Remove Temp Folder
os.rmdir(TEMP_FOLDER)