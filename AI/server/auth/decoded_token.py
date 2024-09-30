# DB 관련 CRUD 작업 함수 정의
from fastapi import Depends, Header
from jose import JWTError, jwt, ExpiredSignatureError
import logging
import base64
from errors.custom_exceptions import InvalidJWT, ExpiredJWT, SignatureJWT, TokenError


try:
    from core.config import settings
except:
    from config import settings

# 로그 메시지
logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)s - %(levelname)s - %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S')
logger = logging.getLogger(__name__)

# 인증을 위한 환경변수 세팅
ALGORITHM = "HS256"

# BASE64로 인코딩된 JWT_SECRET 디코딩
JWT_SECRET = base64.urlsafe_b64decode(settings.JWT_SECRET)


# Bearer token 추출 및 디코딩
def get_token_from_header(authorization: str = Header(...)):
    if not authorization:
        logger.debug("Token not corret format")
        raise InvalidJWT()
    token = authorization.split("Bearer ")[1]
    return token


# Token에서 member id 가져오기
async def get_current_member(token: str = Depends(get_token_from_header)):
    if isinstance(token, dict):
        return token

    try:
        logger.debug(f"Attempting to decode token: {token}")
        decoded_payload = jwt.decode(token, JWT_SECRET, algorithms=[ALGORITHM])
        
        if not isinstance(decoded_payload, dict) or "sub" not in decoded_payload:
            logger.debug("Invalid payload format")
            raise InvalidJWT()

        member_id: int = decoded_payload.get("sub")
        if member_id is None:
            logger.debug("Member Id not found in decoded token")
            raise InvalidJWT()
        
        logger.debug(f"Decoded memberId from token: {member_id}")
        return member_id

    except ExpiredSignatureError:
        logger.error(f"Token expired: {token}")
        raise ExpiredJWT()

    except JWTError as e:
        logger.error(f"JWTError occurred: {e}. Token: {token}, JWT_SECRET: {JWT_SECRET}")
        raise InvalidJWT()

    except Exception as e:
        logger.error(f"An error occurred: {e}")
        raise InvalidJWT()