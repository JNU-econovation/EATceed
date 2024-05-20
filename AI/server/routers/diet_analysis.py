# 식습관 분석 router
from fastapi import APIRouter, HTTPException, Depends
from sqlalchemy.orm import Session
from db.database import get_db
from db.crud import get_user_data
from apis.api import analyze_diet, weight_predict
from db.crud import get_user_data
from auth.decoded_token import get_current_member
import logging

# 로그 메시지
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)

analysis = APIRouter(
    prefix="/analyze_diet"
)

# 로그 메시지
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)

# 체중 예측 라우터
@analysis.get("/weight_predict", tags=['analysis'])
def weight_predict_route(db: Session = Depends(get_db), member_id: int = Depends(get_current_member)):
    try:
        user_data = get_user_data(db, member_id)
        result = weight_predict(user_data)
        logger.debug(f"Member ID {member_id} requested weight prediction")
        return {"prediction": result, "requested_by": member_id}
    except Exception as e:
        logger.error(f"Error predicting weight: {e}")
        raise HTTPException(status_code=500, detail="Weight prediction failed")

# 프롬프트 분석 라우터
@analysis.get("/{prompt_type}", tags=['analysis'])
def analyze_diet_route(prompt_type: str, db: Session = Depends(get_db), member_id: int = Depends(get_current_member)):
    try:
        user_data = get_user_data(db, member_id)
        result = analyze_diet(prompt_type, user_data)
        logger.debug(f"Member ID {member_id} requested diet analysis for prompt type {prompt_type}")
        return {"analysis": result, "requested_by": member_id}
    except Exception as e:
        logger.error(f"Error analyzing diet: {e}")
        raise HTTPException(status_code=500, detail="Diet analysis failed")