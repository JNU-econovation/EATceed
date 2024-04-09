# 데이터베이스 Table 구성
from sqlalchemy import Column, Integer, String, DateTime, Float, Text, ForeignKey, BigInteger, Boolean, Double
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base
from db.database import engine
from sqlalchemy.orm import registry