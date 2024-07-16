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
