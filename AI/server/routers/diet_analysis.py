# 식습관 분석 router
from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from db.database import get_db
from db.crud import get_latest_eat_habits, get_user_data
from auth.decoded_token import get_current_member
import logging
from errors.custom_exceptions import TokenError

# 로그 메시지
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)

analysis = APIRouter(
    prefix="/v1"
)

# 로그 메시지
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)

# 전체 분석 라우터
@analysis.get("/analyze_diet", tags=['analysis'])
def full_analysis_route(db: Session = Depends(get_db), member_id: int = Depends(get_current_member)):
    # 인증 확인
    if not member_id:
            raise TokenError("유효하지 않은 인증입니다")
    
    user_data, avg_calorie = get_user_data(db, member_id)

    # 영양 성분 값이 모두 0인 경우의 에러 메시지 처리
    if isinstance(user_data, dict) and not user_data["success"]:
        return user_data

    latest_eat_habits = get_latest_eat_habits(db, member_id)
    response = {
        "success": True,
        "response": {
            "avg_calorie" : avg_calorie,
            "weight_prediction": latest_eat_habits.WEIGHT_PREDICTION,
            "advice_carbo": latest_eat_habits.ADVICE_CARBO,
            "advice_protein": latest_eat_habits.ADVICE_PROTEIN,
            "advice_fat": latest_eat_habits.ADVICE_FAT,
            "synthesis_advice": latest_eat_habits.SYNTHESIS_ADVICE
        },
        "error": None
        }
    return response