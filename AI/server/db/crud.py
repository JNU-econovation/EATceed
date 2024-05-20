# DB CRUD 함수 정의
from sqlalchemy.orm import Session
from datetime import datetime, timedelta
from db.models import EatHabits, Member, Food, Meal, MealFood

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
    logger.debug(f"member info for member_id : {member_id}")
    member = db.query(Member).filter(Member.MEMBER_PK == member_id).first()

    if member:
        logger.debug(f"Member found: {member}")
    else:
        logger.debug(f"Member not found for member_id: {member_id}")
    return member

# TDEE 수식을 구하기 위한 사용자 신체정보 조회
def get_member_body_info(db: Session, member_id: int):
    # get_member_info() 반환값 사용
    member = get_member_info(db, member_id)

    if member:
        body_info = {
            'gender' : member.MEMBER_GENDER,
            'age' : member.MEMBER_AGE,
            'height' : member.MEMBER_HEIGHT,
            'weight' : member.MEMBER_WEIGHT,
            'activity' : member.MEMBER_ACTIVITY
        }
        return body_info
    else:
        return None
    
# 일주일간 MEAL_TYPE 조회
def get_last_weekend_meals(db: Session, member_id: int):
    now = datetime.now()
    # 지난 주 월요일 0시
    start_of_this_week = now - timedelta(days=now.weekday(), weeks=1)  
    # 이번 주 월요일 0시
    start_of_next_week = start_of_this_week + timedelta(weeks=1)  
    meals = db.query(Meal).filter(Meal.MEMBER_FK == member_id, Meal.CREATED_DATE >= start_of_this_week, Meal.CREATED_DATE < start_of_next_week).all()
    logger.debug(f"Meals found: {meals}")
    return meals

# MEAL_FK에 해당하는 음식 조회
def get_meal_foods(db: Session, meal_id: int):
    meal_foods = db.query(MealFood).filter(MealFood.MEAL_FK == meal_id).all()
    logger.debug(f"Meal foods found: {meal_foods}")
    return meal_foods

# FOOD_FK에 해당하는 음식 정보 조회
def get_food_info(db: Session, food_id: int):
    food = db.query(Food).filter(Food.FOOD_PK == food_id).first()
    logger.debug(f"Food found: {food}")
    return food


# 최종적으로 얻고자하는 사용자에 따른 7일간의 영양성분의 평균값 얻기
def get_member_meals_avg(db: Session, member_id: int):
    member = get_member_info(db, member_id)
    if not member:
        raise Exception("Member not found")

    meals = get_last_weekend_meals(db, member_id)
    total_nutrition = {
        "calorie": 0,
        "carbohydrate": 0,
        "fat": 0,
        "protein": 0,
        "serving_size": 0,
        "sugars": 0,
        "dietary_fiber": 0,
        "sodium": 0,
    }
    total_foods = 0

    for meal in meals:
        meal_foods = get_meal_foods(db, meal.MEAL_PK)
        for meal_food in meal_foods:
            food_info = get_food_info(db, meal_food.FOOD_FK)
            if food_info:
                total_nutrition["calorie"] += food_info.FOOD_CALORIE
                total_nutrition["carbohydrate"] += food_info.FOOD_CARBOHYDRATE
                total_nutrition["fat"] += food_info.FOOD_FAT
                total_nutrition["protein"] += food_info.FOOD_PROTEIN
                total_nutrition["serving_size"] += food_info.FOOD_SERVING_SIZE
                total_nutrition["sugars"] += food_info.FOOD_SUGARS
                total_nutrition["dietary_fiber"] += food_info.FOOD_DIETARY_FIBER
                total_nutrition["sodium"] += food_info.FOOD_SODIUM
                total_foods += 1

    if total_foods > 0:
        avg_nutrition = {key: value / total_foods for key, value in total_nutrition.items()}
    else:
        avg_nutrition = total_nutrition

    return avg_nutrition