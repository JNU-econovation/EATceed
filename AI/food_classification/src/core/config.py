# 환경변수 설정
from dotenv import load_dotenv
import os

load_dotenv()

class Settings:

    JWT_SECRET = os.getenv("JWT_SECRET")

settings = Settings()