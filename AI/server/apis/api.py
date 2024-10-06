# 메인 로직 작성
from openai import OpenAI
import os
import logging
import pandas as pd
from sqlalchemy.orm import Session
from db.database import get_db
from db.crud import create_eat_habits, get_user_data, update_flag, get_all_member_id
from apscheduler.schedulers.background import BackgroundScheduler
from errors.custom_exceptions import UserDataError, AnalysisError

from langchain.agents.agent_types import AgentType
from langchain_experimental.agents.agent_toolkits import create_pandas_dataframe_agent
from langchain_openai import ChatOpenAI

# 로그 메시지
logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)s - %(levelname)s - %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S')
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

def get_completion(prompt, model="gpt-4o-mini"):
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
        raise UserDataError("유저 데이터 에러입니다")

# 식습관 조언 함수 (조언 프롬프트)
def analyze_advice(prompt_type, user_data):
    try:
        prompt_file = os.path.join(PROMPT_PATH, f"{prompt_type}.txt")
        prompt = read_prompt(prompt_file)
        # df = pd.read_csv(DATA_PATH, encoding='cp949')
        # weight_change = weight_predict(user_data)
        
        # 프롬프트 변수 설정
        탄수화물 = user_data['user'][8]['탄수화물(g)']
        단백질 = user_data['user'][6]['단백질(g)']
        지방 = user_data['user'][7]['지방(g)']
        나트륨 = user_data['user'][11]['나트륨(mg)']
        식이섬유 = user_data['user'][9]['식이섬유(g)']
        당류 = user_data['user'][10]['당류(g)']
        
        prompt = prompt.format(탄수화물=탄수화물, 단백질=단백질, 지방=지방, 나트륨=나트륨, 식이섬유=식이섬유, 당류=당류)

        # logger.debug(f"Generated prompt: {prompt}")
        completion = get_completion(prompt)
        return completion
    except Exception as e:
        logger.error(f"Error in analyze_diet function: {e}")
        raise AnalysisError("식습관 분석을 실행할 수 없습니다")

# 식습관 분석 함수 (판단 프롬프트)
def analyze_diet(prompt_type, user_data):
    try:
        prompt_file = os.path.join(PROMPT_PATH, f"{prompt_type}.txt")
        prompt = read_prompt(prompt_file)
        df = pd.read_csv(DATA_PATH, encoding='cp949')
        weight_change = weight_predict(user_data)
        
        # 프롬프트 변수 설정
        성별 = user_data['user'][0]['성별']
        나이 = user_data['user'][1]['나이']
        신장 = user_data['user'][2]['신장']
        체중 = user_data['user'][3]['체중']
        신체활동지수 = user_data['user'][12]['신체활동지수']
        탄수화물 = user_data['user'][8]['탄수화물(g)']
        단백질 = user_data['user'][6]['단백질(g)']
        지방 = user_data['user'][7]['지방(g)']
        
        prompt = prompt.format(성별=성별, 나이=나이, 신장=신장, 체중=체중, 신체활동지수=신체활동지수,
                               탄수화물=탄수화물, 단백질=단백질, 지방=지방)
        
        # agent에 전달할 데이터 설정
        if weight_change == '증가':
            df = df[df['체중변화'] < 0] # 데이터에서 체중이 감소한 경우
        else:
            df = df[df['체중변화'] > 0] # 데이터에서 체중이 증가한 경우
        
        # langchain의 create_pandas_dataframe_agent 사용
        agent = create_pandas_dataframe_agent(
        ChatOpenAI(temperature=0, model="gpt-4o-mini", openai_api_key=OPENAI_API_KEY),
        df=df,
        verbose=True,
        agent_type=AgentType.OPENAI_FUNCTIONS,
        allow_dangerous_code=True
        )
        
        completion = agent.invoke(prompt)
        return completion
        
    except Exception as e:
        logger.error(f"Error in analyze_diet function: {e}")
        raise AnalysisError("식습관 분석을 실행할 수 없습니다")

def full_analysis(db: Session, member_id: int):
    try:
        user_data = get_user_data(db, member_id)

        # 체중 예측
        weight_result = weight_predict(user_data)
        user_data['weight_change'] = weight_result
        avg_calorie = user_data['user'][5]["에너지(kcal)"]

        # 각 프롬프트에 대해 분석 수행
        prompt_types = ['health_advice', 'weight_carbo', 'weight_fat', 'weight_protein']
        analysis_results = {}
        for prompt_type in prompt_types:
            if prompt_type == 'health_advice': # 조언 프롬프트는 analyze_advice 함수
                result = analyze_advice(prompt_type, user_data)
                analysis_results[prompt_type] = result
            else: # 판단 프롬프트는 analyze_diet 함수
                result = analyze_diet(prompt_type, user_data)
                analysis_results[prompt_type] = result['output']

        # DB에 결과값 저장
        create_eat_habits(
            db=db,
            member_id=member_id,
            weight_prediction=weight_result,
            advice_carbo=analysis_results['weight_carbo'],
            advice_protein=analysis_results['weight_protein'],
            advice_fat=analysis_results['weight_fat'],
            synthesis_advice=analysis_results['health_advice'],
            flag=True,
            avg_calorie=avg_calorie
        )

        logger.info(f"Insert success ")

    except ValueError as e:
        logger.error(f"Value error during analysis: {e}")
        raise UserDataError("유저 데이터 에러입니다")
    except Exception as e:
        logger.error(f"Error during analysis: {e}")
        raise AnalysisError("식습관 분석을 실행할 수 없습니다")


# scheduling 
def scheduled_task():
    db: Session = next(get_db())
    try:
        # 모든 기존 레코드의 FLAG를 0으로 업데이트
        update_flag(db)

        # 전체 분석 작업 수행
        member_ids = get_all_member_id(db)
        for member_id in member_ids:
            full_analysis(db=db, member_id=member_id)

    except Exception as e:
        logger.error(f"Error during scheduled task: {e}")
    finally:
        db.close()

# APScheduler 설정
scheduler = BackgroundScheduler()

# test를 위한 시간 설정
# scheduler.add_job(scheduled_task, 'interval', minutes=5)

# 실제 기능 수행 시간 설정
scheduler.add_job(scheduled_task, 'cron', day_of_week='mon', hour=0, minute=0)
scheduler.start()


# 음식 이미지 분석 API: prompt_type은 함수명과 동일
def food_image_analyze(prompt_type: str, image_url: str):
    
    # prompt 타입 설정
    prompt_file = os.path.join(PROMPT_PATH, f"{prompt_type}.txt")
    prompt = read_prompt(prompt_file)

    # OpenAI API 호출
    response = client.chat.completions.create(
        model="gpt-4",
        messages=[
            {"role": "system", "content": prompt},
            {"role": "user", "content": [
                {"type": "image_url", 
                 "image_url": {
                     "url": image_url}}
            ]}
        ],
        temperature=0.0,
        max_tokens=150
    )
    
    result = response.choices[0].message.content

    return result