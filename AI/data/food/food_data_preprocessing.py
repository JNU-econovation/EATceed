# ---
# jupyter:
#   jupytext:
#     formats: ipynb,py:light
#     text_representation:
#       extension: .py
#       format_name: light
#       format_version: '1.5'
#       jupytext_version: 1.16.1
#   kernelspec:
#     display_name: eatceed2
#     language: python
#     name: eatceed2
# ---

# ## 1. 라이브러리 설치

# pandas 설치
import pandas as pd

# +
# 환경변수 
from dotenv import load_dotenv
import os

load_dotenv()

file_path = os.environ.get('RAW_DATA_PATH')
