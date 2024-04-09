# Router Test
from fastapi import APIRouter
from db.crud import *

test = APIRouter(prefix='/test')

# Decoded Token Check
@test.get('/token', tags=['test'])
async def decoded_token_test(member_id: int = Depends(get_current_member)):
    return {"memberId" : member_id}