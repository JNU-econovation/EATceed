# 사용자 유효성 예외처리
class InvalidUserId(Exception):
    def __init__(self, user_id: str):
        self.user_id: str = user_id

# 사용자 데이터 예외처리
class UserDataError(Exception):
    def __init__(self, message: str):
        self.message: str = message

# 분석 예외처리
class AnalysisError(Exception):
    def __init__(self, message: str):
        self.message: str = message

# 토큰 예외처리
class TokenError(Exception):
    def __init__(self, message: str):
        self.message: str = message

class InvalidJWT(TokenError):
    def __init__(self):
        super().__init__("잘못된 토큰입니다.")

class ExpiredJWT(TokenError):
    def __init__(self):
        super().__init__("만료된 토큰입니다.")

class UnsupportedJWT(TokenError):
    def __init__(self):
        super().__init__("지원되지 않는 토큰입니다.")

class SignatureJWT(TokenError):
    def __init__(self):
        super().__init__("토큰의 형식이 잘못 됐습니다.")

class NeedAuthentication(TokenError):
    def __init__(self):
        super().__init__("인증이 필요합니다.")