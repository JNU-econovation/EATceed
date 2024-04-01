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
# -

# ## 2. 데이터 수집
# - 데이터 수집 후 필요 속성 null이 없는 데이터셋 구축

# ### 공공 데이터 포털
# - https://www.data.go.kr/data/15100070/standard.do?recommendDataYn=Y

# csv파일 Load
try:
    df = pd.read_csv(file_path + '전국통합식품영양성분정보 표준데이터.csv', encoding='utf-8')
except UnicodeDecodeError:
    try:
        df = pd.read_csv(file_path + '전국통합식품영양성분정보 표준데이터.csv', encoding='cp949')
    except Exception as e:
        print(f"Error: {e}")

# ### 식품의약품안전처
# - https://various.foodsafetykorea.go.kr/nutrient/




