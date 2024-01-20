import openai
from fastapi import FastAPI, HTTPException, Depends
from typing import Annotated
from pydantic import BaseModel


app = FastAPI(
    title="Exceed Food-Chatbot",
    description="API that use fine-tuning ChatGPT Model as a chatbot",
    version="1.0.0"
)

