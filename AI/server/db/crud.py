from sqlalchemy.orm import Session
from datetime import datetime
from db.models import Message 
from db.schemas import MessageCreate

def create_chat_message(db: Session, user_input: str, bot_response: str, member_id: int):
    created_date = datetime.now()
    db_message = Message(
        CREATED_DATE=created_date,  
        MEMBER_QUESTION=user_input,
        CHAT_BOT_RESPONSE=bot_response,
        MEMBER_FK=member_id
    )
    db.add(db_message)
    db.commit()
    db.refresh(db_message)
    return db_message
