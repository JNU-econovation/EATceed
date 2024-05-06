from fastapi import FastAPI
from routers.test import *
from routers.docs import *

app = FastAPI(
    title="EATceed",
    description="API that use food classification and eating habits analysis"
)

# Router 설정
 
# test.py
app.include_router(test) 

# docs.py
app.include_router(docs)


# API Server Test
@app.get("/")
async def read_root():
    return {"Hello" : "World"}
