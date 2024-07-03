# pytorch/torchserve 이미지는 아래 코드를 통해 다운로드 가능
# $ sudo docker pull pytorch/torchserve:latest
FROM pytorch/torchserve:latest


# setting workdir in the container
WORKDIR /detect_model

# change user and change permissions
USER root
RUN chmod 777 -R . 

# torchserve 실행에 필요한 파일 복사
COPY ./labels /detect_model/labels
COPY ./model /detect_model/model
COPY ./src /detect_model/src
COPY ./requirements.txt /detect_model/requirements.txt

# install the requirements
RUN pip install -r /detect_model/requirements.txt

# apt-get update에서 오류가 발생하여 추가한 코드
# 시간 관련 설정을 변경
RUN echo "Acquire::Check-Valid-Until \"false\";\nAcquire::Check-Date \"false\";" | cat > /etc/apt/apt.conf.d/10no--check-valid-until

RUN apt-get update
RUN apt install zip unzip 

# create mar
RUN bash /detect_model/src/create-mar.sh 

ENV JWT_SECRET="secretKeydlqslekdsecretKeydlqslekdsecretKeydlqslekdsecretKeydlqslekdsecretKeydlqslekdsecretKeydlqslekdse"

# start the server
CMD ["torchserve", "--start", "--models", "all", "--ts-config", "/detect_model/src/config.properties", "--no-config-snapshots"]
# ENTRYPOINT