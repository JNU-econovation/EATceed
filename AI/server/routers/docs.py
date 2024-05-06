from fastapi import FastAPI, Depends, HTTPException,status, APIRouter
from fastapi.security import HTTPBasic, HTTPBasicCredentials
import secrets
from starlette.responses import HTMLResponse
from fastapi.openapi.utils import get_openapi
from fastapi.openapi.docs import get_swagger_ui_html
from typing import Annotated
import logging

# 로그 메시지
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)

docs = APIRouter(prefix='/docs')

# HTTP 기본 보안 스키마 초기화
security = HTTPBasic()

# 관리자 인증 함수
def get_admin(credentials: Annotated[HTTPBasicCredentials, Depends(security)]):
    correct_username = secrets.compare_digest(credentials.username, "user")
    correct_password = secrets.compare_digest(credentials.password, "password")
    if not (correct_username and correct_password):
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Incorrect email or password",
            headers={"WWW-Authenticate": "Basic"},
        )
    return ""

@docs.get("/", include_in_schema=False)
async def get_documentation(admin: str = Depends(get_admin)):
    return get_swagger_ui_html(openapi_url="/docs/openapi.json", title="docs")

@docs.get("/openapi.json", include_in_schema=False)
async def openapi(app: FastAPI, admin: str = Depends(get_admin)):
    return get_openapi(title=app.title, version=app.version, routes=app.routes)



