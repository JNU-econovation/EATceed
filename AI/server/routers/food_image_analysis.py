import base64
from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from pydantic import BaseModel, HttpUrl, ValidationError
from apis.api import food_image_analyze
from auth.decoded_token import get_current_member
from db.database import get_db
from errors.custom_exceptions import InvalidJWT

router = APIRouter(
    prefix="/v1/ai/food_image_analysis",
    tags=["음식 이미지 분석"]
)

# 음식 이미지 분석 API 테스트
@router.post("/test")
async def food_image_analysis_test():
    return {"success": "성공"}


# 리팩토링 과정에서 pydantic 위치 변경 진행할 예정
class ImageAnalysisRequest(BaseModel):
    # base64에 따른 문자열 타입 설정 
    image_base64: str


# 음식 이미지 분석 API
@router.post("/")
async def anlyze_food_image(request: ImageAnalysisRequest,
                            db: Session = Depends(get_db), member_id: int = Depends(get_current_member)):
    
    # 인증 확인
    if not member_id:
        raise InvalidJWT()
    
    """
    1. 요청 횟수 제한 구현(Redis)
    추가적인 기능(함수) 작성 필요 : api.py
    """

    # OpenAI API로 Base64 인코딩된 이미지 전송
    try:
        result = food_image_analyze(request.image_base64)
    except ValidationError as e:
        return {"error": f"Invalid Base64 format: {e}"}

    """
    2. food_image_analyze 함수를 통해 얻은 음식명(리스트 값)을 이용해 
    Elasticsearch 유사도 검색을 진행해 유사도가 높은 음식(들) 반환 진행
    추가적인 기능(함수) 작성 필요 : api.py
    """


    return {
        "success": True, 
        "response": result,
        "error": None
    }