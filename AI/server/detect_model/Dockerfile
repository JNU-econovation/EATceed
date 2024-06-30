# pytorch/torchserve 이미지는 아래 코드를 통해 다운로드 가능
# $ sudo docker pull pytorch/torchserve:latest
FROM pytorch/torchserve:latest


# setting workdir in the container
WORKDIR /detect_model

# change user and change permissions
USER root
RUN chmod 777 -R . 


# copy the files in to workdir
COPY ./food_classification/src /detect_model/src

# copy model configuration file
COPY ./model/config/config.properties /detect_model/src/config.properties

# 상위 계층 디렉토리의 파일 복사는 불가능함
# 어차피 병합 후 최상위 디렉토리에서 하나의 dockerfile을 생성할 것이므로,
# 아래에 해당하는 파일은 수동으로 src 내에 복사하여 사용했음
# COPY ../../auth/decoded_token.py /detect_model/src/decoded_token.py
# COPY ../../core/config.py /detect_model/src/config.py

# get model
COPY ./food_classification/ckpt /detect_model/model

# install the requirements
RUN pip install -r /detect_model/src/requirements.txt

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