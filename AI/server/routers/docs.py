from fastapi import FastAPI, Depends, HTTPException,status
from fastapi.security import HTTPBasic, HTTPBasicCredentials
import secrets
from starlette.responses import HTMLResponse
from fastapi.openapi.utils import get_openapi
from fastapi.openapi.docs import get_swagger_ui_html
import logging

# 로그 메시지
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)
