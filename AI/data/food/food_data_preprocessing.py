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

# '데이터구분명'의 값이 '음식'이고, 주어진 컬럼들에 null값이 없는 데이터 추출
required_columns = ['에너지(kcal)', '영양성분함량기준량', '단백질(g)', '지방(g)', '탄수화물(g)', '당류(g)', '나트륨(mg)', '식이섬유(g)']
filtered_df = df[(df['데이터구분명'] == '가공식품') & (df[required_columns].notnull().all(axis=1))]

# 총 인스턴스 개수 파악
total_instances = len(filtered_df)

# 총 인스턴스 개수 : 1175개
print(f"\n총 인스턴스 개수: {total_instances}개")

# +
# # 필터링된 데이터를 CSV 파일로 저장
# # utf-8-sig로 인코딩하여 한글 깨짐 방지
# filtered_df.to_csv('01_filtered_data_공공데이터.csv', index=False, encoding='utf-8-sig')
# -

# ### 식품의약품안전처
# - https://various.foodsafetykorea.go.kr/nutrient/

# **가공식품**

# Excel 파일 Load
df = pd.read_excel(file_path + '식품의약품안전처_가공식품.xlsx')

# 주어진 컬럼들에 null값이 없는 데이터 추출
required_columns = ['에너지\n(kcal)', '영양성분기준용량', '단백질\n(g)', '지방\n(g)', '탄수화물\n(g)', '당류\n(g)', '나트륨\n(mg)', '식이섬유\n(g)']
filtered_df = df[df[required_columns].notnull().all(axis=1)]

# 총 인스턴스 개수 파악
total_instances = len(filtered_df)

print(f"\n총 인스턴스 개수: {total_instances}개")

# +
# # 필터링된 데이터를 CSV 파일로 저장
# # utf-8-sig로 인코딩하여 한글 깨짐 방지
# filtered_df.to_csv('02_filtered_data_식품의약처_가공.csv', index=False, encoding='utf-8-sig')
# -

# **음식**
