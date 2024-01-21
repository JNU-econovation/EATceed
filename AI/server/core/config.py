from dotenv import load_dotenv
import os

load_dotenv() 

class Settings:

    DB_URL = os.getenv("DB_URL")
    SECRET_KEY = os.getenv("SECRET_KEY")
    OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")
    MODEL = os.getenv("MODEL")  
    
settings = Settings()