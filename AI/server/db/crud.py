# DB CRUD 함수 정의
from sqlalchemy.orm import Session
from datetime import datetime
from db.models import EatHabits, Member, Food

import logging

# 로그 메시지
logging.basicConfig(level=logging.DEBUG)
logger = logging.getLogger(__name__)

# DB 연결 Test CRUD 
def crud_test(db: Session, member_id: int, flag: bool, weight_prediction: str, weight_advice: str):
    try:
        created_date = datetime.now()
        logger.debug(f"Attemping to insert Eathabits record for member_id : {member_id}")
        eat_habits = EatHabits(
            CREATED_DATE=created_date,
            FLAG = flag,
            WEIGHT_PREDICTION = weight_prediction,
            WEIGHT_ADVICE = weight_advice,
            MEMBER_FK = member_id
        )
        db.add(eat_habits)
        db.commit()
        db.refresh(eat_habits)
        logger.info(f"Successfully inserted EatHabits record for member_id: {member_id}")
        return eat_habits
    except Exception as e:
        logger.error(f"Error inserting EatHabits record for member_id: {member_id} - {e}")
        db.rollback()
        raise

# member_id에 해당하는 사용자 정보 조회
def get_member_info(db: Session, member_id: int):
    return db.query(Member).filter(Member.MEMBER_PK == member_id).first()


