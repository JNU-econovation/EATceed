#!/bin/bash

mkdir /detect_model/model_store

cd /detect_model/model_store

torch-model-archiver --model-name foodcls \
                    --version 1.0 \
                    --serialized-file /detect_model/model/model_param.pt \
                    --extra-files "/detect_model/model/yolov3-spp-403cls.cfg,\
                                   /detect_model/labels/403food.names,\
                                   /detect_model/labels/food_labels.csv,\
                                   /detect_model/labels/food_data.csv,\
                                   /detect_model/src/auth/decoded_token.py,\
                                   /detect_model/src/core/config.py" \
                                   /detect_model/src/utils/darknet.py,\
                                   /detect_model/src/utils/torch_utils.py,\
                    --handler /detect_model/src/handler.py \
                    --requirements-file /detect_model/requirements.txt \
                    --force