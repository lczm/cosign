###  Defaults  ###
CSV_RAW = 'raw.csv'
CSV_BASE = 'base.csv'
CSV_USER = 'user.csv'
DATASET_VID = 'dataset-vid'
DATASET_IMG = 'dataset-img'

DOWNSCALED_GLOSS = [
    'ONE', 'TWO', 'THREE', 'FOUR', 'FIVE', 'SIX', 'SEVEN', 'EIGHT', 
    'NINE', 'TEN', 'ELEVEN', 'TWELVE', 'THIRTEEN', 'FOURTEEN', 
    'FIFTEEN', 'SIXTEEN', 'SEVENTEEN', 'EIGHTEEN', 'NINETEEN', 'TWENTY',
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
]
DOWNLOAD_CHUNK_SIZE = 8192
DOWNLOAD_TIMEOUT = 5
PROCESS_FRAME_WIDTH = 640
PROCESS_FRAME_HEIGHT = 480

###  Imports  ###
import pandas as pd
import requests
import cv2
import os, time

# 1. CSV Processing
def process_raw_csv():
    data = pd.read_csv(
        CSV_RAW, 
        usecols=['Consultant', 'Main New Gloss', 'Gloss Variant', 'Session', 'Scene', 'Start', 'End']
    )
    data.rename(inplace=True, columns={
        'Consultant': 'consultant',
        'Main New Gloss': 'gloss',
        'Gloss Variant': 'gloss_variant',
        'Session': 'session', 
        'Scene': 'scene', 
        'Start': 'start', 
        'End': 'end'
    })
    data = data[(data.gloss != '============') & (data.gloss != '------------')]
    data.to_csv(CSV_BASE, index=False)

# 2. CSV Downscaling
def downscale_base_csv():
    data = pd.read_csv(CSV_BASE)
    data = data[data.gloss.isin(DOWNSCALED_GLOSS)]
    print('###  Gloss Value Counts  ###')
    print(data.gloss.value_counts())
    print('###  Session Scene Repeats  ###')
    print(data.groupby(['session', 'scene']).size().reset_index(name='Freq'))
    data.to_csv('user.csv', index=False)

# 3. Dataset Download
def cvt2url(session, scene_no, camera_no=1):
    return f'http://csr.bu.edu/ftp/asl/asllvd/asl-data2/quicktime/' \
            '{session}/scene{scene_no}-camera{camera_no}.mov'

def download_file(url, file_path):
    print(f'Downloading {file_path}')
    print(f'From {url}')
    count = 0
    while True:
        count += 1
        print(f'Attempt #{count}')
        try:
            with requests.get(url, stream=True, timeout=DOWNLOAD_TIMEOUT) as r:
                r.raise_for_status()
                with open(file_path, 'wb') as f:
                    for chunk in r.iter_content(chunk_size=DOWNLOAD_CHUNK_SIZE):
                        if chunk:
                            f.write(chunk)
        except:
            print('Connection Error: Retrying in 5 secs...')
            time.sleep(5)
        else:
            break
    print('Download Completed')

def download_data():
    data = pd.read_csv(CSV_USER)
    if not os.path.isdir(DATASET_VID): os.mkdir(DATASET_VID)
    sessions_scenes = data.groupby(['session', 'scene']).size().index
    for index, (session_name, scene_no) in enumerate(sessions_scenes):
        print(f'Downloading {index}/{len(sessions_scenes)}: Session {session_name} Scene {scene_no}')
        filename = f'{session_name}-{scene_no}.mov'
        filepath = os.path.join(DATASET_VID, filename)
        if os.path.isfile(filepath):
            print(f'Skipped: {filepath} already exists')
            continue
        url = cvt2url(session_name, scene_no)
        download_file(url, filepath)

# 4. Data Processing
def segment_vid_imgs(session_name, scene_no, starts, ends):
    read_file = os.path.join(DATASET_VID, f'{session_name}-{scene_no}.mov')
    print(f'Processing from {read_file}')
    if not os.path.isfile(read_file):
        print(f'File Error: {read_file} not found')
        return None
    cap = cv2.VideoCapture(read_file)
    
    frame_no = 0
    for start, end in zip(starts, ends):
        # Skip unused frames
        while frame_no < start:
            ret, _ = cap.read()    
            if not ret: raise Exception('Start time out of video')
            
            frame_no += 1
        
        # Current frame
        while frame_no <= end:
            ret, frame = cap.read()
            if not ret: raise Exception('End time out of video')
                
            write_file = os.path.join(DATASET_IMG, f'{session_name}-{scene_no}-{frame_no}.png')
            if os.path.isfile(write_file):
                print(f'Skipped: {write_file} already exists')
            else:
                cv2.imwrite(write_file, frame)
                print(f'Created: {write_file}')
            frame_no += 1
    cap.release()

def check_overlap_safe(starts, ends):
    iter_starts = iter(starts)
    iter_ends = iter(ends)
    next(iter_starts)
    for x, y in zip(iter_starts, iter_ends):
        if x < y:
            return False
        elif x == y:
            raise Exception('Frame Overlap Special Condition Met')
    return True

def process_data():
    if not os.path.isdir(DATASET_IMG: os.mkdir(DATASET_IMG)
    data = pd.read_csv(CSV_USER)
    sessions_scenes = data.groupby(['session', 'scene']).size().index
    for index, (session_name, scene_no) in enumerate(sessions_scenes):
        acts = data[(data.session == session_name) & (data.scene == scene_no)].sort_values('start')
        print(f'Processing {index}/{len(sessions_scenes)}: Session {session_name} Scene {scene_no}')
        overlap_safe = check_overlap_safe(acts.start, acts.end)
        if not overlap_safe:
            print('Frame Overlap Unsafe: Session Skipped')
            continue
        segment_vid_imgs(session_name, scene_no, acts.start, acts.end)


###  Command Line Interface  ###
import argparse
parser = argparse.ArgumentParser()
parser.add_argument('-c', '--convert-csv',  help='convert raw csv for use', action='store_true')
parser.add_argument('-s', '--strip-csv',    help='strip (downscale) csv based on desired gloss', action='store_true')
parser.add_argument('-d', '--download-vids', help='download the videos', action='store_true')
parser.add_argument('-p', '--process-vids', help='segment the videos to produce required images', action='store_true')
parser.add_argument('--raw-csv',            help='filename for raw csv')
parser.add_argument('--base-csv',           help='filename for base csv')
parser.add_argument('--user-csv',           help='filename for user csv')
parser.add_argument('--dataset-vid',        help='directory for dataset videos')
parser.add_argument('--dataset-img',        help='directory for dataset images')
parser.add_argument('--downscaled-gloss',   help='gloss names for downscaling', nargs='*')
parser.add_argument('--chunk-size',         help='chunk size for stream request')
parser.add_argument('--timeout',            help='timeout for dead connection')
parser.add_argument('--frame-width',        help='image frame width')
parser.add_argument('--frame-height',       help='image frame height')
args = parser.parse_args()

if args.raw_csv:            CSV_RAW = args.raw_csv
if args.base_csv:           CSV_BASE = args.base_csv
if args.user_csv:           CSV_USER = args.user_csv
if args.dataset_vid:        DATASET_VID = args.dataset_vid
if args.dataset_img:        DATASET_IMG = args.dataset_img
if args.downscaled_gloss:   DOWNSCALED_GLOSS = args.downscaled_gloss
if args.chunk_size:         DOWNLOAD_CHUNK_SIZE = args.chunk_size
if args.timeout:            DOWNLOAD_TIMEOUT = args.timeout
if args.frame_width:        PROCESS_FRAME_WIDTH = args.frame_width
if args.frame_height:       PROCESS_FRAME_HEIGHT = args.frame_height

if args.convert_csv:        process_raw_csv()
if args.strip_csv:          downscale_base_csv()
if args.download_vids:      download_data()
if args.process_vids:       process_data()