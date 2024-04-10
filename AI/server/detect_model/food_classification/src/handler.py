import logging
import os
from collections import Counter

import io
import base64
import torch
from torchvision import transforms
from xml.etree.ElementTree import Element, SubElement, ElementTree
import numpy as np
import platform as pf
import psutil
import PIL
import pandas as pd
import seaborn as sns
from darknet import *

from ts.torch_handler.base_handler import BaseHandler
from ts.torch_handler.object_detector import ObjectDetector

logger = logging.getLogger(__name__)


class ModelHandler(ObjectDetector):
    def __init__(self):
        super(BaseHandler, self).__init__()

    def initialize(self, context):
        # Set device type
        self.device = torch.device("cpu")

        # Load the model
        properties = context.system_properties
        self.manifest = context.manifest
        self.model_dir = properties.get("model_dir")
        
        # print(self.model_dir)

        self.imgsz = 320
        self.names = os.path.join(self.model_dir, "403food.names")
        self.iou_thres = 0.5
        self.conf_thres = 0.3
        self.classes = None
        self.agnostic_nms = False
        self.augment = False
        self.out = os.path.join(self.model_dir, "output")
        self.save_xml = True
        self.model_pt_path = None
        self.object_names = []
        self.tree = None
        
        # if os.path.exists(self.out):
        #     shutil.rmtree(self.out)  # delete output folder
        # os.makedirs(self.out)  # make new output folder

        if "serializedFile" in self.manifest["model"]:
            serialized_file = self.manifest["model"]["serializedFile"]
            self.model_pt_path = os.path.join(self.model_dir, serialized_file)
        
        self.model = self._load_torchscript_model(self.model_pt_path)
        logger.debug("Model file %s loaded successfully", self.model_pt_path)

        self.initialized = True    
    
    
    def _load_torchscript_model(self, model_pt_path):
        """Loads the PyTorch model and returns the NN model object.

        Args:
            model_pt_path (str): denotes the path of the model file.

        Returns:
            (NN Model Object) : Loads the model object.
        """
        # TODO: remove this method if https://github.com/pytorch/text/issues/1793 gets resolved

        config = os.path.join(self.model_dir, 'yolov3-spp-403cls.cfg')
        model = Darknet(cfg=config, img_size=self.imgsz)
        
        model.load_state_dict(torch.load(model_pt_path, map_location=self.device)['model'], strict=False)
        
        model.to(self.device).eval()
        
        return model
    
    
    def preprocess(self, data):
        """The preprocess function of MNIST program converts the input data to a float tensor

        Args:
            data (List): Input data from the request is in the form of a Tensor

        Returns:
            list : The preprocess function returns the input image as a list of float tensors.
        """
        images = []
        image_processing = transforms.Compose([transforms.ToTensor(), transforms.Resize((self.imgsz, self.imgsz))])

        for row in data:
            # Compat layer: normally the envelope should just return the data
            # directly, but older versions of Torchserve didn't have envelope.
            image = row.get("data") or row.get("body")
            if isinstance(image, str):
                # if the image is a string of bytesarray.
                image = base64.b64decode(image)

            # If the image is sent as bytesarray
            if isinstance(image, (bytearray, bytes)):
                image = Image.open(io.BytesIO(image))
                image = image_processing(image)
            else:
                # if the image is a list
                image = torch.FloatTensor(image)

            images.append(image)

        return torch.stack(images).to(self.device)
    
   
    def inference(self, data):
        save_img = True

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
            
            if object not in ['00000000', 'spoon']:
                print('this is object:', object)
                results.append(labels[str(object)])
            else:
                continue
        return results