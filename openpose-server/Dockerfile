from cwaffles/openpose

RUN apt-get update
RUN apt-get update
RUN apt-get install -y apt-transport-https
RUN apt-get upgrade -y
RUN apt-get install -y python3 python3-dev python3-pip \
	python3-wheel python3-setuptools
RUN apt-get install -y libsm6 libxrender1 libfontconfig1 libxext6

COPY requirements.txt ./
RUN pip3 install --no-cache-dir -r requirements.txt

COPY hand_inference_graph ./build/examples/hand_inference_graph/
COPY pickleModels ./build/examples/tutorial_api_python
COPY utility.py ./build/examples/tutorial_api_python

WORKDIR ./build/examples/tutorial_api_python
CMD ["python3", "utility.py"]
