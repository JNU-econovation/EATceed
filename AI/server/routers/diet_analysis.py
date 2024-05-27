# 식습관 분석 router
from fastapi import APIRouter, HTTPException, Depends
from sqlalchemy.orm import Session
from db.database import get_db
from db.crud import get_user_data, create_eat_habits
from apis.api import analyze_diet, weight_predict
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

# 전체 분석 라우터
@analysis.get("/", tags=['analysis'])
def full_analysis_route(db: Session = Depends(get_db), member_id: int = Depends(get_current_member)):
    try:
        user_data = get_user_data(db, member_id)
        
        # 체중 예측
        weight_result = weight_predict(user_data)
        user_data['weight_change'] = weight_result
        
        # 각 프롬프트에 대해 분석 수행
        prompt_types = ['health_advice', 'weight_carbo', 'weight_fat', 'weight_protein']
        analysis_results = {}
        for prompt_type in prompt_types:
            result = analyze_diet(prompt_type, user_data, weight_result)
            analysis_results[prompt_type] = result

        logger.debug(f"Member ID {member_id} requested full analysis")
        logger.debug(f"Analysis results: {analysis_results}")

        # DB에 결과값 저장
        eat_habits_record = create_eat_habits(
            db=db,
            member_id=member_id,
            weight_prediction=weight_result,
            advice_carbo=analysis_results['weight_carbo'],
            advice_protein=analysis_results['weight_protein'],
            advice_fat=analysis_results['weight_fat'],
            synthesis_advice=analysis_results['health_advice'],
            flag=True
        )

        logger.info(f"EatHabits record created with ID: {eat_habits_record.ID}")

        return {
            'weight_predict': weight_result,
            'analysis': analysis_results
        }
    except ValueError as e:
        logger.error(f"Value error during analysis: {e}")
        raise HTTPException(status_code=400, detail=str(e))
    except Exception as e:
        logger.error(f"Error during analysis: {e}")
        raise HTTPException(status_code=500, detail="Analysis failed")