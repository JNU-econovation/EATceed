# DB CRUD 함수 정의
from sqlalchemy.orm import Session
from datetime import datetime
from db.models import EatHabits

# DB 연결 Test CRUD 
def crud_test(db: Session, member_id: int, flag: bool, weight_prediction: str, weight_advice: str):
    created_date = datetime.now()
    eat_habits = EatHabits(
        CREATED_DATE=created_date,
        MEMBER_FK = member_id,
        FLAG = flag,
        WEIGHT_PREDICTION = weight_prediction,
        WEIGHT_ADVICE = weight_advice
    )
    db.add(eat_habits)
    db.commit()
    db.refresh(eat_habits)
    return eat_habits