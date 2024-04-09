# 데이터베이스 Table 구성
from sqlalchemy import Column, Integer, String, DateTime, Float, Text, ForeignKey, BigInteger, Boolean, Double
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base
from db.database import engine
from sqlalchemy.orm import registry

registry = registry()
Base = declarative_base()

# MEMBER_TB 구성
class Member(Base):
    __tablename__ = 'MEMBER_TB'

    MEMBER_PK = Column(BigInteger, primary_key=True, autoincrement=True)
    CREATED_DATE = Column(DateTime, nullable=False)
    UPDATED_DATE = Column(DateTime, nullable=False)
    MEMBER_ACTIVITY = Column(String(255))
    MEMBER_AGE = Column(Integer)
    MEMBER_ETC = Column(String(255))
    MEMBER_GENDER = Column(Boolean)
    MEMBER_HEIGHT = Column(Double)
    MEMBER_EMAIL = Column(String(255), unique=True, nullable=False)
    MEMBER_PASSWORD = Column(String(255), nullable=False)
    MEMBER_ROLE = Column(String(255))
    MEMBER_CHECKED = Column(Boolean, nullable=False)

    weights = relationship('Weight', back_populates='member')
    meals = relationship('Meal', back_populates='member')
    eat_habits = relationship('EatHabits', back_populates='member')

# WEIGHT_TB 구성
class Weight(Base):
    __tablename__ = 'WEIGHT_TB'

    WEIGHT_PK = Column(BigInteger, primary_key=True, autoincrement=True)
    CREATED_DATE = Column(DateTime, nullable=False)
    UPDATED_DATE = Column(DateTime, nullable=False)
    WEIGHT_WEIGHT = Column(Double)
    WEIGHT_TARGET_WEIGHT = Column(Double)
    MEMBER_FK = Column(BigInteger, ForeignKey('MEMBER_TB.MEMBER_PK'))

    member = relationship('Member', back_populates='weights')


# FOOD_TB 구성
class Food(Base):
    __tablename__ = 'FOOD_TB'

    FOOD_PK = Column(BigInteger, primary_key=True, autoincrement=True)
    FOOD_CALORIE = Column(Double, nullable=False)
    FOOD_CARBOHYDRATE = Column(Double, nullable=False)
    FOOD_FAT = Column(Double, nullable=False)
    FOOD_MAIN_CATEGORY = Column(String(255), nullable=False)
    FOOD_NAME = Column(String(255), nullable=False)
    FOOD_PROTEIN = Column(Double, nullable=False)
    FOOD_SERVING_SIZE = Column(Double, nullable=False)
    FOOD_SUB_CATEGORY = Column(String(255), nullable=False)

    meals = relationship('MealFood', back_populates='food')


# MEAL_TB 구성
class Meal(Base):
    __tablename__ = 'MEAL_TB'

    MEAL_PK = Column(BigInteger, primary_key=True, autoincrement=True)
    CREATED_DATE = Column(DateTime, nullable=False)
    UPDATED_DATE = Column(DateTime, nullable=False)
    MEAL_TYPE = Column(String(255), nullable=False)
    MEAL_FOOD_MULTIPLE = Column(Double, nullable=False)
    MEMBER_FK = Column(BigInteger, ForeignKey('MEMBER_TB.MEMBER_PK'))

    member = relationship('Member', back_populates='meals')
    meal_foods = relationship('MealFood', back_populates='meal')


# MEAL_FOOD_TB 구성
class MealFood(Base):
    __tablename__ = 'MEAL_FOOD_TB'

    MEAL_FOOD_PK = Column(BigInteger, primary_key=True, autoincrement=True)
    CREATED_DATE = Column(DateTime, nullable=False)
    UPDATED_DATE = Column(DateTime, nullable=False)
    FOOD_FK = Column(BigInteger, ForeignKey('FOOD_TB.FOOD_PK'))
    MEAL_FK = Column(BigInteger, ForeignKey('MEAL_TB.MEAL_PK'))

    food = relationship('Food', back_populates='meals')
    meal = relationship('Meal', back_populates='meal_foods')


# ANALYSIS_TB 구성
class Analysis(Base):
    __tablename__ = 'ANALYSIS_TB'

    ANALYSIS_PK = Column(BigInteger, primary_key=True, autoincrement=True)
    CREATED_DATE = Column(DateTime, nullable=False)
    WEIGHT_PREDICTION = Column(Text, nullable=False)
    WEIGHT_ADVICE = Column(Text, nullable=False)

    eat_habits = relationship('EatHabits', back_populates='analysis')


# EAT_HABITS_TB 구성
class EatHabits(Base):
    __tablename__ = 'EAT_HABITS_TB'

    EAT_HABITS_PK = Column(BigInteger, primary_key=True, autoincrement=True)
    CREATED_DATE = Column(DateTime, nullable=False)
    ANALYSIS_FK = Column(BigInteger, ForeignKey('ANALYSIS_TB.ANALYSIS_PK'))
    CHAT_BOT_RESPONSE = Column(Text, nullable=False)
    MEMBER_FK = Column(BigInteger, ForeignKey('MEMBER_TB.MEMBER_PK'))

    member = relationship('Member', back_populates='eat_habits')
    analysis = relationship('Analysis', back_populates='eat_habits')

# 테이블을 생성하고 매퍼를 추가
Base.metadata.create_all(bind=engine)

registry.configure()