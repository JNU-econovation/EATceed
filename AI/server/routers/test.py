# Router Test
from fastapi import APIRouter

test = APIRouter(prefix='/test')

# DB
@test.get('/', tags=['test'])
async def db_test():
    pass