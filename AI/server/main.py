import openai
from fastapi import FastAPI, HTTPException, Depends
from typing import Annotated
from pydantic import BaseModel
from starlette import status
import logging
from sqlalchemy.orm import Session


app = FastAPI(
    title="Exceed Food-Chatbot",
    description="API that use fine-tuning ChatGPT Model as a chatbot",
    version="1.0.0"
)


class UserInput(BaseModel):
    user_input: str

prompt=f"""
You are a food recommendation chatbot. The purpose of this chatbot is to make food recommendations based on the user's current situation, emotions, and dietary preferences, such as vegan or vegetarian, and follow these guidelines

1. If the question is not about food, display "해당 챗봇은 음식 추천에 관한 챗봇입니다. 관련된 질문을 보내주세요!" Print
2. identify the question and explain why you chose the food you did if you're going to recommend it.
3. suggest 3 to 4 food options
4. Always print 3 lines for answers
5. The user's situation may be a general request for food recommendations such as breakfast, lunch, and dinner, but it may also be a specific situation such as recommending food for 4 men or recommending food to eat with a girlfriend, so if there is a description of the situation in the question, understand it properly and recommend food accordingly.
6. If the question asks you to recommend food based on your sense of taste (flavor), understand your sense of taste and recommend food accordingly.
7. use the term "cool" when referring to something cold, such as a cold drink, but also when referring to something cold, such as bokkeotguk the day after drinking alcohol, or when referring to a spicy soup with lots of chili peppers or kukbap.
8. Prioritize food recommendations when a question asks for a specific diet, such as vegetarian, vegan, etc.
9. If a user asks for price recommendations, respond that the chatbot doesn't have information on prices and therefore can't make recommendations.
10. When a user receives a food recommendation response, selects a food, and then asks for a harmonized food recommendation, recommend 2-3 foods and explain why they are harmonized.
11. Always use honorifics when answering questions.
12. Write a detailed reason for your food recommendation
"""


chat_responses = []

chat_log = [{'role':'system',
            'content': prompt}]

# 로깅 설정 추가
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)

def handle_exception(e: Exception) -> HTTPException:
    # 디버깅을 위해 예외 세부 정보를 로그에 남깁니다.
    logger.error(f"An error occured: {str(e)}")

    # HTTPException으로 변환
    if isinstance(e, HTTPException):
        return e

    # 에러가 발생한 경우 더 구체적인 HTTP 응답을 반환합니다.
    return HTTPException(
        status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
        detail={"success": False, "error": str(e)},
    )


@app.post("/v1/chat", status_code=status.HTTP_201_CREATED)
async def chat(user_input: UserInput):
    try:
        chat_log.append({'role': 'user', 'content': user_input.user_input})
        chat_responses.append(user_input.user_input)

        # 속성들 추가 및 조정하기
        responses = openai.chat.completions.create(
            model=model, # 'gpt-3.5-turbo-1106'
            messages=chat_log,
            temperature=0.1,
            max_tokens=500
        )

        bot_response = responses.choices[0].message.content
        chat_log.append({'role': 'assistant', 'content': bot_response})
        delivery_format = {
            "success": True,
            "responses" : {
                "answer" : bot_response
            },
            "error": None
        }

        create_chat_message(db, user_input.user_input, bot_response, member_id)
        return delivery_format
    except Exception as e:
        return handle_exception(e)
