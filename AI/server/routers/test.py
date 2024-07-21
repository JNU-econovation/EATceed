# Router Test
from fastapi import APIRouter, Depends, HTTPException
from sqlalchemy.orm import Session
from db.schema import EatHabits
from auth.decoded_token import get_current_member
from db.crud import crud_test
from db.database import get_db


import logging

# 로그 메시지
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)
    

test = APIRouter(prefix='/test')

# Decoded Token Check
@test.get('/token', tags=['test'])
async def decoded_token_test(member_id: int = Depends(get_current_member)):
    logger.debug(f"Decoded token and obtained member_id: {member_id}")
    return {"memberId" : member_id}

# DB CRUD check
@test.post('/crud', tags=['test'])
async def db_crud_test(
    eat_habits_data: EatHabits,
    db: Session = Depends(get_db), 
    member_id: int = Depends(get_current_member)
):
    try:
        logger.debug(f"Starting DB CRUD test for member_id: {member_id} with data: {eat_habits_data}")
        result = crud_test(
            db, 
            member_id, 
            eat_habits_data.flag, 
            eat_habits_data.weight_prediction, 
            eat_habits_data.advice_carbo,
            eat_habits_data.advice_protein,
            eat_habits_data.advice_fat,
            eat_habits_data.synthesis_advice
            )
        logger.info(f"DB CRUD for successful for member_id: {member_id}")
        return {"message": "Test record created successfully", "record": result}
    except Exception as e:
        logger.error(f"DB CRUD test failed for member_id: {member_id} - {e}")
        raise HTTPException(status_code=500, detail=str(e))