from fastapi.responses import JSONResponse
from fastapi import Request, status, FastAPI
from .custom_exceptions import InvalidUserId, UserDataError, AnalysisError, TokenError

# 응답 형식 클래스
class ErrorResponse:
    def __init__(self, success: bool, response: None, code: int, reason: str, status: int):
        self.success = success
        self.response = response
        self.reason = reason
        self.code = code
        self.status = status

    def dict(self):
        return {
            "success": self.success,
            "response": self.response,
            "error": {
                "code": self.code,
                "reason": self.reason,
                "status": self.status
            }
        }

def get_error_response(error_message: str, error_code: int, status_code: int):
    return ErrorResponse(success=False, response=None, reason=error_message, code=error_code, status=status_code).dict()

def register_exception_handlers(app: FastAPI):

    @app.exception_handler(InvalidUserId)
    async def invalid_user_id_exception_handler(request: Request, exc: InvalidUserId):
        return JSONResponse(
            status_code=status.HTTP_400_BAD_REQUEST,
            content=get_error_response(exc.message, 1001, 400)
        )

    @app.exception_handler(UserDataError)
    async def user_data_error_handler(request: Request, exc: UserDataError):
        return JSONResponse(
            status_code=status.HTTP_400_BAD_REQUEST,
            content=get_error_response(exc.message, 1002, 400)
        )

    @app.exception_handler(AnalysisError)
    async def analysis_error_handler(request: Request, exc: AnalysisError):
        return JSONResponse(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            content=get_error_response(exc.message, 1003, 500)
        )

    @app.exception_handler(TokenError)
    async def token_error_handler(request: Request, exc: TokenError):
        return JSONResponse(
            status_code=status.HTTP_401_UNAUTHORIZED,
            content=get_error_response(exc.message, 1004, 401)
        )