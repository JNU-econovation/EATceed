# 데이터베이스 Table 구성
from sqlalchemy import Column, Integer, String, DateTime, Text, ForeignKey, BigInteger, Boolean, Double
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base
from db.database import engine
from sqlalchemy.orm import registry

registry = registry()
Base = declarative_base()

# MEMBER_TB 구성
class Member(Base):
    __tablename__ = "MEMBER_TB"

    MEMBER_PK = Column(BigInteger, primary_key=True, index=True, autoincrement=True)
    CREATED_DATE = Column(DateTime(6), nullable=False)
    UPDATED_DATE = Column(DateTime(6), nullable=False)
    MEMBER_ACTIVITY = Column(String(255), nullable=True)
    MEMBER_AGE = Column(Integer, nullable=True)
    MEMBER_ETC = Column(String(255), nullable=True)
    MEMBER_GENDER = Column(Integer, nullable=True)
    MEMBER_HEIGHT = Column(Double, nullable=True)
    MEMBER_EMAIL = Column(String(255), nullable=False, unique=True)
    MEMBER_PASSWORD = Column(String(255), nullable=False)
    MEMBER_ROLE = Column(String(255), nullable=False, default='MEMBER')
    MEMBER_WEIGHT = Column(Double, nullable=True)
    MEMBER_TARGET_WEIGHT = Column(Double, nullable=True)
    MEMBER_CHECKED = Column(Boolean, nullable=False, default=False)

    foods = relationship("Food", back_populates="member")
    meals = relationship("Meal", back_populates="member")
    eat_habits = relationship("EatHabits", back_populates="member")
    histories = relationship("History", back_populates="member")

# FOOD_TB 구성
class Food(Base):
    __tablename__ = "FOOD_TB"

    FOOD_PK = Column(BigInteger, primary_key=True, index=True, autoincrement=True)
    FOOD_CALORIE = Column(Double, nullable=False)
    FOOD_CARBOHYDRATE = Column(Double, nullable=False)
    FOOD_FAT = Column(Double, nullable=False)
    FOOD_NAME = Column(String(255), nullable=False)
    FOOD_PROTEIN = Column(Double, nullable=False)
    FOOD_SERVING_SIZE = Column(Double, nullable=False)
    FOOD_SUGARS = Column(Double, nullable=False)
    FOOD_DIETARY_FIBER = Column(Double, nullable=False)
    FOOD_SODIUM = Column(Double, nullable=False)
    MEMBER_FK = Column(BigInteger, ForeignKey('MEMBER_TB.MEMBER_PK'), nullable=True)

    member = relationship("Member", back_populates="foods")

# MEAL_TB 구성
class Meal(Base):
    __tablename__ = "MEAL_TB"

    MEAL_PK = Column(BigInteger, primary_key=True, index=True, autoincrement=True)
    CREATED_DATE = Column(DateTime(6), nullable=False)
    UPDATED_DATE = Column(DateTime(6), nullable=False)
    MEAL_TYPE = Column(String(255), nullable=False)
    MEMBER_FK = Column(BigInteger, ForeignKey('MEMBER_TB.MEMBER_PK'), nullable=True)

    member = relationship("Member", back_populates="meals")
    meal_foods = relationship("MealFood", back_populates="meal")

# MEAL_FOOD_TB 구성
class MealFood(Base):
    __tablename__ = "MEAL_FOOD_TB"

    MEAL_FOOD_PK = Column(BigInteger, primary_key=True, index=True, autoincrement=True)
    CREATED_DATE = Column(DateTime(6), nullable=False)
    UPDATED_DATE = Column(DateTime(6), nullable=False)
    FOOD_FK = Column(BigInteger, ForeignKey('FOOD_TB.FOOD_PK'), nullable=True)
    MEAL_FK = Column(BigInteger, ForeignKey('MEAL_TB.MEAL_PK'), nullable=True)
    MEAL_FOOD_MULTIPLE = Column(Double, nullable=True)
    MEAL_FOOD_G = Column(Integer, nullable=True)

    food = relationship("Food")
    meal = relationship("Meal", back_populates="meal_foods")

# EAT_HABITS_TB
class EatHabits(Base):
    __tablename__ = "EAT_HABITS_TB"

    EAT_HABITS_PK = Column(BigInteger, primary_key=True, index=True, autoincrement=True)
    MEMBER_FK = Column(BigInteger, ForeignKey('MEMBER_TB.MEMBER_PK'), nullable=True)
    CREATED_DATE = Column(DateTime(6), nullable=False)
    FLAG = Column(Boolean, nullable=False)
    WEIGHT_PREDICTION = Column(Text, nullable=False)
    ADVICE_CARBO = Column(Text, nullable=False)
    ADVICE_PROTEIN = Column(Text, nullable=False)
    ADVICE_FAT = Column(Text, nullable=False)
    SYNTHESIS_ADVICE = Column(Text, nullable=False)
    AVG_CALORIE = Column(Double, nullable=False)

    member = relationship("Member", back_populates="eat_habits")

# HISTORY_TB 구성
class History(Base):
    __tablename__ = "HISTORY_TB"

    HISTORY_PK = Column(BigInteger, primary_key=True, index=True, autoincrement=True)
    CREATED_DATE = Column(DateTime(6), nullable=False)
    UPDATED_DATE = Column(DateTime(6), nullable=False)
    HISTORY_ACTIVITY = Column(String(255), nullable=False)
    HISTORY_AGE = Column(Integer, nullable=False)
    HISTORY_GENDER = Column(Integer, nullable=False)
    HISTORY_HEIGHT = Column(Double, nullable=False)
    HISTORY_WEIGHT = Column(Double, nullable=False)
    MEMBER_FK = Column(BigInteger, ForeignKey('MEMBER_TB.MEMBER_PK'), nullable=True)

    member = relationship("Member", back_populates="histories")

# NOTIFY_TB 구성
class Notify(Base):
    __tablename__ = "NOTIFY_TB"

    NOTIFY_PK = Column(BigInteger, primary_key=True, index=True, autoincrement=True)
    CREATED_DATE = Column(DateTime(6), nullable=False)
    UPDATED_DATE = Column(DateTime(6), nullable=False)
    NOTIFY_URL = Column(String(255), nullable=False)
    NOTIFY_IS_READ = Column(Boolean, nullable=False)  # bit(1) -> Boolean으로 매핑
    NOTIFY_CONTENT = Column(String(255), nullable=False)
    NOTIFY_TYPE = Column(String(255), nullable=False)
    MEMBER_FK = Column(BigInteger, ForeignKey('MEMBER_TB.MEMBER_PK'), nullable=True)

    # Relationships
    member = relationship("Member", back_populates="notifications")

# 테이블을 생성하고 매퍼를 추가
Base.metadata.create_all(bind=engine)

registry.configure()