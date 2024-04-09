from fastapi import FastAPI
from routers.test import *

app = FastAPI(
    title="EATceed",
    description="API that use food classification and eating habits analysis"
)

# Router 설정
app.include_router(test)

# API Server Test
@app.get("/")
async def read_root():
    return {"Hello" : "World"}


