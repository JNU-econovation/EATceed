# 식습관 분석 router
from fastapi import APIRouter, HTTPException, Depends
from sqlalchemy.orm import Session
from db.database import get_db
from db.crud import get_user_data, create_eat_habits, get_latest_eat_habits
from apis.api import analyze_diet, weight_predict
from auth.decoded_token import get_current_member
import logging

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
    try:
        latest_eat_habits = get_latest_eat_habits(db, member_id)
        if latest_eat_habits:
            response ={
                'weight_prediction' : latest_eat_habits.WEIGHT_PREDICTION,
                'advice_carbo' : latest_eat_habits.ADVICE_CARBO,
                'advice_protein' : latest_eat_habits.ADVICE_PROTEIN,
                'advice_fat' : latest_eat_habits.ADVICE_FAT,
                'synthesis_advice' : latest_eat_habits.SYNTHESIS_ADVICE
            }
            return response
        else:
            raise HTTPException(status_code=404, detail="No analysis found for the given member_id")
    except HTTPException as http_exc:
        logger.error(f"HTTP error fetching analysis for member_id: {member_id} - {http_exc.detail}")
        raise http_exc
    except Exception as e:
        logger.error(f"Error fetching analysis for member_id: {member_id} - {e}")
        raise HTTPException(status_code=500, detail="Error fetching analysis")
