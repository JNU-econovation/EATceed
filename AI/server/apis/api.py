# 메인 로직 작성
from openai import OpenAI
import os
import logging
import pandas as pd

# 로그 메시지
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)

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
    logger.debug(f"Prompt sent to OpenAI: {prompt}")
    logger.debug(f"Response from OpenAI: {response.choices[0].message.content}")
    return response.choices[0].message.content


# 체중 예측 함수
def weight_predict(user_data: dict) -> str:
    try:
        logger.debug(f"user_data in weight_predict: {user_data}")
        energy = user_data['user'][5]["에너지(kcal)"]
        tdee = user_data['user'][13]["TDEE"]
        if energy > tdee:
            return '증가'
        else:
            return '감소'
    except (KeyError, IndexError, TypeError) as e:
        logger.error(f"Error in weight_predict function: {e}")
        raise ValueError("Invalid user data structure")

# 식습관 분석 함수
def analyze_diet(prompt_type, user_data, weight_change):
    prompt_file = os.path.join(PROMPT_PATH, f"{prompt_type}.txt")
    prompt = read_prompt(prompt_file)
    df = pd.read_csv(DATA_PATH, encoding='cp949')
    weight_change = weight_predict(user_data)
    prompt = prompt.format(user_data=user_data, df=df, weight_change=weight_change)

    # logger.debug(f"Generated prompt: {prompt}")
    completion = get_completion(prompt)
    return completion