import openai
from fastapi import FastAPI, HTTPException, Depends
from pydantic import BaseModel
from starlette import status
import logging
from sqlalchemy.orm import Session
from db.crud import create_chat_message
from db.database import engine, get_db, Base
from core.config import settings
from jose import JWTError, jwt
from fastapi.security import OAuth2AuthorizationCodeBearer


Base.metadata.create_all(bind=engine)


openai.api_key = settings.OPENAI_API_KEY
model = settings.MODEL
SECRET_KEY = settings.SECRET_KEY
ALGORITHM = "HS256"


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

@app.middleware("http")
async def log_errors(request, call_next):
    try:
        return await call_next(request)
    except HTTPException as exc:
        logger.error(f"HTTPException: {exc.detail}")
        raise
    except Exception as e:
        logger.error(f"An error occurred: {str(e)}")
        raise


oauth2_scheme = OAuth2AuthorizationCodeBearer(tokenUrl="token", authorizationUrl="authorize")


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

def get_current_member(token: str = Depends(oauth2_scheme)) -> int:
    try:
        logger.debug(f"Received token: {token}")

        # 디코딩된 payload를 인쇄하는 이 줄을 추가하세요.
        decoded_payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM], options={"verify_signature": False})
        logger.debug(f"Decoded payload: {decoded_payload}")

        member_id: int = decoded_payload.get("memberId")
        if member_id is None:
            raise HTTPException(
                status_code=status.HTTP_401_UNAUTHORIZED,
                detail="Could not validate credentials",
                headers={"WWW-Authenticate": "Bearer"},
            )
        return member_id
    except jwt.ExpiredSignatureError as expired_error:
        logger.error(f"Expired Signature Error: {expired_error}")
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Expired Signature Error",
            headers={"WWW-Authenticate": "Bearer"},
        )
    except JWTError as jwt_error:
        logger.error(f"JWT Error: {jwt_error}")
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="JWT Error",
            headers={"WWW-Authenticate": "Bearer"},
        )


def handle_jwt_verification_failure(token: str):
    # 서명 검증 실패 시 수행할 작업을 정의합니다.
    logger.error(f"JWT Signature Verification Failed for token: {token}")
    
    # 클라이언트에게 적절한 응답을 반환합니다.
    raise HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="JWT Signature Verification Failed",
        headers={"WWW-Authenticate": "Bearer"},
    )


@app.post("/v1/chat", status_code=status.HTTP_201_CREATED)
async def chat(
    user_input: UserInput,
    member_id: int = Depends(get_current_member),
    db: Session = Depends(get_db),
):
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