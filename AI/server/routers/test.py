# Router Test
from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from pydantic import BaseModel
from auth.decoded_token import get_current_member
from db.crud import crud_test
from db.database import get_db

import logging

class EatHabitsCreate(BaseModel):
    flag: bool
    weight_prediction: str
    weight_advice: str
    


test = APIRouter(prefix='/test')

# 로그 메시지
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)

# Decoded Token Check
@test.get('/token', tags=['test'])
async def decoded_token_test(member_id: int = Depends(get_current_member)):
    return {"memberId" : member_id}

# DB CRUD check
@test.post('/crud', tags=['test'])
async def db_crud_test(
    eat_habits_data: EatHabitsCreate,
    db: Session = Depends(get_db), 
    member_id: int = Depends(get_current_member)
):
    result = crud_test(
        db, 
        member_id, 
        eat_habits_data.flag, 
        eat_habits_data.weight_prediction, 
        eat_habits_data.weight_advice)
    return {"message": "Test record created successfully", "record": result}
