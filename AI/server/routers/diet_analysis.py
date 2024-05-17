# 식습관 분석 router

from apis.api import *
from fastapi import APIRouter, HTTPException, Depends
import logging
from auth.decoded_token import get_current_member

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
def weight_predict_route(member_id: int = Depends(get_current_member)):
    try:
        result = weight_predict(intake_json_1)
        logger.debug(f"Member ID {member_id} requested weight prediction")
        return {"prediction": result, "requested_by": member_id}
    except Exception as e:
        logger.error(f"Error predicting weight: {e}")
        raise HTTPException(status_code=500, detail="Weight prediction failed")

# 프롬프트 분석 라우터
@analysis.get("/{prompt_type}", tags=['analysis'])
def analyze_diet_route(prompt_type: str, member_id: int = Depends(get_current_member)):
    try:
        result = analyze_diet(prompt_type)
        logger.debug(f"Member ID {member_id} requested diet analysis for prompt type {prompt_type}")
        return {"analysis": result, "requested_by": member_id}
    except Exception as e:
        logger.error(f"Error analyzing diet: {e}")
        raise HTTPException(status_code=500, detail="Diet analysis failed")