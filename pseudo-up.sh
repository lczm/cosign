#/bin/bash

docker run -d -p 5000:5000 db-server
docker run -d --gpus all -p 80:80 openpose-server

