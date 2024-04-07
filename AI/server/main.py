from fastapi import FastAPI

app = FastAPI(
    title="EATceed",
    description="API that use food classification and eating habits analysis"
)

@app.get("/")
async def read_root():
    return {"Hello" : "World"}

