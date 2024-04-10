# Router Test
from fastapi import APIRouter, Depends
from sqlalchemy.orm import Session
from auth.decoded_token import get_current_member
from db.crud import crud_test

test = APIRouter(prefix='/test')

# Decoded Token Check
@test.get('/token', tags=['test'])
async def decoded_token_test(member_id: int = Depends(get_current_member)):
    return {"memberId" : member_id}

# DB CRUD check
@test.post('/crud', tags=['test'])
async def db_crud_test(
    db: Session = Depends(), 
    member_id: int = Depends(get_current_member)
):
    # Test value
    flag = True
    weight_prediction = "70kg"
    weight_advice = "Maintain a balanced diet"

    result = crud_test(db, member_id, flag, weight_prediction, weight_advice)
    return {"message": "Test record created successfully", "record": result}
