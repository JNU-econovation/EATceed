# 식습관 분석 router
from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from db.database import get_db
from db.crud import get_latest_eat_habits, get_user_data
from auth.decoded_token import get_current_member
import logging
from errors.custom_exceptions import InvalidJWT, ExpiredJWT, UserDataError

# 로그 메시지
logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)s - %(levelname)s - %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S')
logger = logging.getLogger(__name__)

router = APIRouter(
    prefix="/v1/ai/diet_analysis",
    tags=["식습관 분석"]
)

# 전체 분석 라우터
@router.get("/")
def full_analysis_route(db: Session = Depends(get_db), member_id: int = Depends(get_current_member)):
    # 인증 확인
    if not member_id:
            raise InvalidJWT()

    latest_eat_habits = get_latest_eat_habits(db, member_id)
    if not latest_eat_habits:
         raise UserDataError("유저 데이터 에러입니다")

    response = {
        "success": True,
        "response": {
            "avg_calorie" : latest_eat_habits.AVG_CALORIE,
            "weight_prediction": latest_eat_habits.WEIGHT_PREDICTION,
            "advice_carbo": latest_eat_habits.ADVICE_CARBO,
            "advice_protein": latest_eat_habits.ADVICE_PROTEIN,
            "advice_fat": latest_eat_habits.ADVICE_FAT,
            "synthesis_advice": latest_eat_habits.SYNTHESIS_ADVICE
        },
        "error": None
        }
    return response