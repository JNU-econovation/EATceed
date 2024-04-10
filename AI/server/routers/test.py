# Router Test
from fastapi import APIRouter, Depends
from auth.decoded_token import get_current_member

test = APIRouter(prefix='/test')

# Decoded Token Check
@test.get('/token', tags=['test'])
async def decoded_token_test(member_id: int = Depends(get_current_member)):
    return {"memberId" : member_id}