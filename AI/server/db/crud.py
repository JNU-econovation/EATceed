# DB 관련 CRUD 작업 함수 정의
from sqlalchemy.orm import Session
from fastapi import Depends, HTTPException, status
from fastapi.security import OAuth2PasswordBearer
from jose import JWTError, jwt

from core.config import settings

# 인증을 위한 환경변수 세팅
JWT_SECRET = settings.JWT_SECRET
ALGORITHM = "HS256"

# bearer token 해석 
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")

# DB에서 member id 가져오기
async def get_current_member(token: str = Depends(oauth2_scheme)):
    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Could not validate credentials",
        headers={"WWW-authenticate" : "Bearer"}
    )
    try:
        decoded_payload = jwt.decode(token, JWT_SECRET, algorithms=[ALGORITHM])
        member_id: int = decoded_payload.get("memberId")
        if member_id is None:
            raise credentials_exception
        return member_id
    except JWTError:
        raise credentials_exception