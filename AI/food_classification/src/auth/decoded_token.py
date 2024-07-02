# DB 관련 CRUD 작업 함수 정의
from fastapi import Depends, HTTPException, status, Header
from jose import JWTError, jwt, ExpiredSignatureError
import logging
import base64

try:
    from core.config import settings
except:
    from config import settings

# 로그 메시지
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)

# 인증을 위한 환경변수 세팅
ALGORITHM = "HS256"

# BASE64로 인코딩된 JWT_SECRET 디코딩
JWT_SECRET = base64.urlsafe_b64decode(settings.JWT_SECRET)


# Bearer token 추출 및 디코딩
def get_token_from_header(authorization: str = Header(...)):
    if not authorization:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Authorization header is missing",
            headers={"WWW-authenticate": "Bearer"},
        )
    token = authorization.split("Bearer ")[1]
    return token

# Token에서 member id 가져오기
async def get_current_member(token: str = Depends(get_token_from_header)):
    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Could not validate credentials",
        headers={"WWW-authenticate": "Bearer"}
    )

    try:
        print(f"Attempting to decode token: {token}")
        # Token Decode
        decoded_payload = jwt.decode(token, JWT_SECRET, algorithms=[ALGORITHM])
        
        # 페이로드 검증
        if not isinstance(decoded_payload, dict) or "sub" not in decoded_payload:
            logger.debug("Invalid payload format")
            raise credentials_exception

        # member_id 가져오기
        member_id: int = decoded_payload.get("sub")
        if member_id is None:
            logger.debug("Member Id not found in decoded token")
            raise credentials_exception
        print(f"Decoded memberId from token: {member_id}")
        return member_id
    except ExpiredSignatureError:
        # 토큰 만료 예외 처리
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Token expired",
            headers={"WWW-authenticate": "Bearer"}
        )
    except JWTError as e:
        logger.error(f"JWTError occurred: {e}. Token: {token}, JWT_SECRET: {JWT_SECRET}")
        raise credentials_exception
    except Exception as e:
        logger.error(f"An error occurred: {e}")
        raise credentials_exception

