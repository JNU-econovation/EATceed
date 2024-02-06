from sqlalchemy import Column, ForeignKey, Integer, String, Float, DateTime, Text, Table
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base
from db.database import engine
from sqlalchemy.orm import registry

registry = registry()
Base = declarative_base() 

class Member(Base):
    __tablename__ = 'MEMBER_TB'
    MEMBER_PK = Column(Integer, primary_key=True, autoincrement=True)
    CREATED_DATE = Column(DateTime, nullable=False)
    UPDATED_DATE = Column(DateTime, nullable=False)
    MEMBER_ACTIVITY = Column(String(255), nullable=False)
    MEMBER_AGE = Column(Integer, nullable=False)
    MEMBER_ETC = Column(String(255), nullable=False)
    MEMBER_GENDER = Column(Integer, nullable=False)
    MEMBER_HEIGHT = Column(Float, nullable=False)
    MEMBER_LOGIN_ID = Column(String(255), nullable=False)
    MEMBER_PASSWORD = Column(String(255), nullable=False)
    MEMBER_ROLE = Column(String(255), nullable=False)
    MEMBER_WEIGHT = Column(Float, nullable=False)
    # meals 속성 추가
    meals = relationship('Meal', back_populates='member')
    # messages 속성 추가
    messages = relationship('Message', back_populates='member')

class Food(Base):
    __tablename__ = 'FOOD_TB'
    FOOD_PK = Column(Integer, primary_key=True, autoincrement=True)
    FOOD_CALORIE = Column(Float, nullable=False)
    FOOD_CARBOHYDRATE = Column(Float, nullable=False)
    FOOD_FAT = Column(Float, nullable=False)
    FOOD_MAIN_CATEGORY = Column(String(255), nullable=False)
    FOOD_NAME = Column(String(255), nullable=False)
    FOOD_PROTEIN = Column(Float, nullable=False)
    FOOD_SERVING_SIZE = Column(Float, nullable=False)
    FOOD_SUB_CATEGORY = Column(String(255), nullable=False)

class Meal(Base):
    __tablename__ = 'MEAL_TB'
    MEAL_PK = Column(Integer, primary_key=True, autoincrement=True)
    CREATED_DATE = Column(DateTime, nullable=False)
    UPDATED_DATE = Column(DateTime, nullable=False)
    MEAL_TYPE = Column(String(255), nullable=False)
    MEAL_FOOD_MULTIPLE = Column(Float, nullable=False)
    MEMBER_FK = Column(Integer, ForeignKey('MEMBER_TB.MEMBER_PK'))
    member = relationship('Member', back_populates='meals')

class MealFood(Base):
    __tablename__ = 'MEAL_FOOD_TB'
    MEAL_FOOD_PK = Column(Integer, primary_key=True, autoincrement=True)
    CREATED_DATE = Column(DateTime, nullable=False)
    UPDATED_DATE = Column(DateTime, nullable=False)
    FOOD_FK = Column(Integer, ForeignKey('FOOD_TB.FOOD_PK'))
    MEAL_FK = Column(Integer, ForeignKey('MEAL_TB.MEAL_PK'))

class Message(Base):
    __tablename__ = 'MESSAGE_TB'
    MESSAGE_PK = Column(Integer, primary_key=True, autoincrement=True)
    CREATED_DATE = Column(DateTime, nullable=False)
    MEMBER_QUESTION = Column(Text, nullable=False)
    CHAT_BOT_RESPONSE = Column(Text, nullable=False)
    MEMBER_FK = Column(Integer, ForeignKey('MEMBER_TB.MEMBER_PK'))
    member = relationship('Member', back_populates='messages')

# 테이블을 생성하고 매퍼를 추가
Base.metadata.create_all(bind=engine)

registry.configure()