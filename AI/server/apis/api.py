# 메인 로직 작성
from openai import OpenAI
import pandas as pd
import os

# 환경변수 설정
OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")
DATA_PATH = os.getenv("DATA_PATH")
PROMPT_PATH = os.getenv("PROMPT_PATH")

# prompt를 불러오기
def read_prompt(filename):
    with open(filename, 'r', encoding='utf-8') as file:
        prompt = file.read().strip()
    return prompt

# Chatgpt API 사용
client = OpenAI(api_key = OPENAI_API_KEY)

def get_completion(prompt, model="gpt-3.5-turbo"):
    messages = [{"role": "user", "content": prompt}]
    response = client.chat.completions.create(
        model=model,
        messages=messages,
        temperature=0
    )
    return response.choices[0].message.content

# 사용자 데이터 예시
intake_json_1 = { "user" :[
    {"성별":'남성'},
    {"나이":32},
    {"신장":172.1},
    {"체중":82},
    {"식품섭취량":2941.42},
    {"에너지(kcal)":3079},
    {"단백질(g)":154.68},
    {"지방(g)":100.35},
    {"탄수화물(g)":300.16},
    {"식이섬유(g)":29.92},
    {"당류(g)":80.63},
    {"나트륨(mg)":5687.9},
    {"신체활동지수":1},
    {"TDEE": 2197.392}
]} # 증가

# 체중 예측 함수
def weight_predict(intake_json):
  if intake_json['user'][5]["에너지(kcal)"] > intake_json['user'][13]["TDEE"]:
    return '증가'
  else:
    return '감소'

# 식습관 분석 함수
def analyze_diet(prompt_type):
    prompt_file = os.path.join(PROMPT_PATH, f"{prompt_type}.txt")
    prompt = read_prompt(prompt_file)
    df = pd.read_csv(DATA_PATH, encoding='cp949')
    completion = get_completion(prompt)
    return completion
