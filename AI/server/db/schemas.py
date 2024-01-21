from pydantic import BaseModel


class MessageBase(BaseModel):
    created_date: str
    member_question: str
    chat_bot_response: str

 
class MessageCreate(MessageBase):
    pass


class Message(MessageBase):
    message_pk: int
    member_fk: int

    class Config:
        orm_mode = True


