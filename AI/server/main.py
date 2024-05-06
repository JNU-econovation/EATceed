from fastapi import FastAPI, Depends, status
from typing import Annotated
import secrets
from fastapi.security import HTTPBasic, HTTPBasicCredentials
from fastapi.openapi.utils import get_openapi
from fastapi.responses import HTMLResponse, UJSONResponse
from fastapi.openapi.docs import get_swagger_ui_html, get_redoc_html
from routers.test import *
from core.config import settings


app = FastAPI(
    title="EATceed",
    description="API that use food classification and eating habits analysis",
    docs_url=None,
    redoc_url=None,
    openapi_url="/api/openapi.json",
    default_response_class=UJSONResponse,
)

# HTTP 기본 인증을 사용하는 Security 객체 생성
security = HTTPBasic()

def get_current_username(credentials: HTTPBasicCredentials = Depends(security)) -> str:
    correct_username = secrets.compare_digest(credentials.username, settings.ADMIN_USERNAME)
    correct_password = secrets.compare_digest(credentials.password, settings.ADMIN_PASSWORD)
    if not (correct_username and correct_password):
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Incorrect email or password",
            headers={"WWW-Authenticate": "Basic"},
        )
    return credentials.username

@app.get("/docs", response_class=HTMLResponse)
async def get_docs(username: str = Depends(get_current_username)) -> HTMLResponse:
    return get_swagger_ui_html(openapi_url="/api/openapi.json", title="docs")


@app.get("/redoc", response_class=HTMLResponse)
async def get_redoc(username: str = Depends(get_current_username)) -> HTMLResponse:
    return get_redoc_html(openapi_url="/api/openapi.json", title="redoc")


# Router 설정 
# test.py
app.include_router(test) 


# API Server Test
@app.get("/")
async def read_root():
    return {"Hello" : "World"}
