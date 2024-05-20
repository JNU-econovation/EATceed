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


# 체중 예측 함수
def weight_predict(user_data):
  if user_data['user'][5]["에너지(kcal)"] > user_data['user'][13]["TDEE"]:
    return '증가'
  else:
    return '감소'

# 식습관 분석 함수
def analyze_diet(prompt_type, user_data):
    prompt_file = os.path.join(PROMPT_PATH, f"{prompt_type}.txt")
    prompt = read_prompt(prompt_file)
    prompt = prompt.format(user_data=user_data)
    completion = get_completion(prompt)
    return completion
