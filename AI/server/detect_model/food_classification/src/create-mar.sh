#!/bin/bash

mkdir /detect_model/model_store

cd /detect_model/model_store

torch-model-archiver --model-name foodcls \
                    --version 1.0 \
                    --serialized-file /detect_model/model/model_param.pt \
                    --extra-files "/detect_model/src/darknet.py,/detect_model/src/403food.names,/detect_model/src/yolov3-spp-403cls.cfg,/detect_model/src/torch_utils.py,/detect_model/src/food_labels.csv,/detect_model/src/food_data.csv,/detect_model/src/decoded_token.py,/detect_model/src/config.py" \
                    --handler /detect_model/src/handler.py \
                    --requirements-file /detect_model/src/requirements.txt \
                    --force