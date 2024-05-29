import os, io, base64, logging, asyncio, requests, json
import numpy as np
import pandas as pd
from PIL import Image

import torch
from torchvision import transforms

from ts.torch_handler.base_handler import BaseHandler

from darknet import *
from decoded_token import *


logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)


class ModelHandler(BaseHandler):
    def __init__(self):
        super(BaseHandler, self).__init__()
        self.device = torch.device("cpu")
        self.imgsz = 320
        self.iou_thres = 0.5
        self.conf_thres = 0.3
        self.classes = None
        self.agnostic_nms = False
        self.augment = False
        self.model_pt_path = None


    def initialize(self, context):
        self.context = context
        properties = context.system_properties
        manifest = context.manifest
        self.model_dir = properties.get("model_dir")
        self.names = os.path.join(self.model_dir, "403food.names")

        if "serializedFile" in manifest["model"]:
            serialized_file = manifest["model"]["serializedFile"]
            self.model_pt_path = os.path.join(self.model_dir, serialized_file)
        
        self.model = self._load_torchscript_model(self.model_pt_path)
        logger.debug("Model file %s loaded successfully", self.model_pt_path)
    
    
    def _load_torchscript_model(self, model_pt_path):
        # 모델 불러오기
        config = os.path.join(self.model_dir, 'yolov3-spp-403cls.cfg')
        model = Darknet(cfg=config, img_size=self.imgsz)
        
        # 모델 가중치 불러오기
        model.load_state_dict(torch.load(model_pt_path, map_location=self.device)['model'], strict=False)
        model.to(self.device).eval()
        return model
    
    
    def preprocess(self, data):
        images = []
        image_processing = transforms.Compose([transforms.ToTensor(),
                                               transforms.Resize((self.imgsz, self.imgsz))])

        for row in data:
            image = row.get("data") or row.get("url")
            if isinstance(image, str):
                # if the image is a string of bytesarray.
                image = base64.b64decode(image)

            # If the image is sent as bytesarray
            if isinstance(image, (bytearray, bytes)):
                try:
                    is_url = image.decode('utf-8').startswith('http')
                except:
                    is_url = False
                if is_url:
                    response = requests.get(image.decode('utf-8'))
                    image = response.content
                image = Image.open(io.BytesIO(image))
                image = image_processing(image)
            else:
                # if the image is a list
                image = torch.FloatTensor(image)

            images.append(image)

        return torch.stack(images).to(self.device)
    
   
    def inference(self, data):
        img = data[0]
        img = np.ascontiguousarray(img)

        dataset = ("/path", img, img)

        names = load_classes(self.names)
        colors = [[random.randint(0, 255) for _ in range(3)] for _ in range(len(self.names))]
        img = torch.zeros((1, 3, self.imgsz, self.imgsz), device=self.device)  # init img

        path, img, im0s = dataset
        img = torch.from_numpy(img).to(self.device)
        
        if img.ndimension() == 3:
            img = img.unsqueeze(0)

        # Inference
        with torch.no_grad():
            t1 = torch_utils.time_synchronized()
            pred = self.model(img, self.augment)[0]
            t2 = torch_utils.time_synchronized()

        # Apply NMS
        pred = non_max_suppression(pred, self.conf_thres, self.iou_thres,
                                multi_label=False, classes=self.classes, agnostic=self.agnostic_nms)
        
        self.object_names = []
        # Process detections
        for i, det in enumerate(pred):  # detections for image i
            s, im0 = '', im0s
            

            if det is not None and len(det):
                # Rescale boxes from imgsz to im0 size
                det[:, :4] = scale_coords(img.shape[2:], det[:, :4], im0.shape).round()

                # Print results
                for c in det[:, -1].unique():
                    n = (det[:, -1] == c).sum()  # detections per class
                    s += '%g %s, ' % (n, names[int(c)])  # add to string

                total = []
                
                # Write results
                for *xyxy, conf, cls in reversed(det):
                    semi = []
                    for nums in range(4): 
                        str_x = str(xyxy[nums]).split('(')
                        str_x = str_x[1].split('.')
                        semi.append(str_x[0])
                    total.append(semi)
                    self.object_names.append(names[int(cls)])

            # Print time (inference + NMS)
            print('%s (%.3fs)' % (s, t2 - t1))
        
        return self.object_names


    def postprocess(self, object_names):
        df = pd.read_csv('food_labels.csv', encoding='utf-8')
        
        for i in range(len(df)):
            
            len_code = len(str(df['소분류코드'][i]))
            if len_code < 8:
                df.loc[i, '소분류코드'] = str('0'*(8-len_code)) + str(df['소분류코드'][i])
            else:
                df.loc[i, '소분류코드'] = str(df['소분류코드'][i])
                
        labels = dict.fromkeys(list(df['소분류코드']))

        for i in range(len(df)):
            labels[df['소분류코드'][i]] = df['소분류구분'][i]
        
        results = []
        
        for object in object_names:
            # 접시나 spoon이 감지되었다면 이는 결과에서 제외
            if object not in ['00000000', 'spoon']:
                print('this is object:', object)
                results.append(labels[str(object)])
            else:
                continue

        # 감지된 결과가 없을 경우
        if len(results) == 0:
            err = {
                "success": False,
                "response": None,
                "error": {
                    "code": "",     # 4453
                    "reason": "Could not detect object in the image",
                    "status": "404"
                }
            }
            results.append(json.dumps(err))
        return results
    
    
    def handle(self, data, context):
        
        # curl로 보낸 토큰을 받아옴
        token = data[0]['token'].decode("utf-8")
        
        try:
            # 토큰을 디코딩
            asyncio.run(get_current_member(get_token_from_header(token)))
        except Exception as e:
            err = {
                "success": False,
                "response": None,
                "error": {
                    "code": "",     
                    "reason": e.detail,
                    "status": e.status_code
                }
            }
            return [json.dumps(err)]
        
        # 추론 시작 시간
        start_time = time.time()

        self.context = context
        metrics = self.context.metrics

        # 전처리, 추론, 후처리를 차례로 수행
        try:
            data_preprocess = self.preprocess(data)
            output = self.inference(data_preprocess)
        except:
            err = {
                "success": False,
                "response": None,
                "error": {
                    "code": "",     # 4454
                    "reason": "Prediction failed",
                    "status": "503"
                }
            }
            return [json.dumps(err)]
        output = self.postprocess(output)

        # 추론 종료 시간
        stop_time = time.time()
        
        metrics.add_time(
            "HandlerTime", round((stop_time - start_time) * 1000, 2), None, "ms"
        )
        
        return output