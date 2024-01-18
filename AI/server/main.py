import openai
from fastapi import FastAPI
from pydantic import BaseModel
from starlette import status

app = FastAPI(
    title="Exceed Food-Chatbot",
    description="API that use fine-tuning ChatGPT Model as a chatbot",
    version="1.0.0"
)

class UserInput(BaseModel):
    user_input: str

chat_responses = []

chat_log = [{'role':'system',
            'content':"Different situations, people, and emotions will have different food cravings,\
                  and you're good at making food recommendations based on those characteristics."}]

@app.post("/v1/chat/", status_code=status.HTTP_201_CREATED)
async def chat(user_input: UserInput):
    chat_log.append({'role': 'user', 'content': user_input.user_input})
    chat_responses.append(user_input.user_input)

    responses = openai.chat.completions.create(
        model='gpt-3.5-turbo',
        messages=chat_log,
        temperature=0.6
    )


    bot_response = responses.choices[0].message.content
    chat_log.append({'role': 'assistant', 'content': bot_response})
    delivery_format = {
            "success": True,
            "responses" : {
                "answer" : bot_response
            },
            "error": None
        }
    return delivery_format