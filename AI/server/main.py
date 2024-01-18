import openai
from fastapi import FastAPI

app = FastAPI(
    title="Exceed Food-Chatbot",
    description="API that use fine-tuning ChatGPT Model as a chatbot",
    version="1.0.0"
)

chat_responses = []

chat_log = [{'role':'system',
            'content':"Different situations, people, and emotions will have different food cravings,\
                  and you're good at making food recommendations based on those characteristics."}]
