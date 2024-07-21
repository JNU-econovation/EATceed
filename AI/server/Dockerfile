# Python
FROM python:3.9-slim

# 환경 변수 설정
ENV PYTHONUNBUFFERED 1

# 작업 디렉토리 설정
WORKDIR /app

# requirements.txt 
COPY requirements.txt .

# 종속성 설치
RUN pip install --no-cache-dir -r requirements.txt

# 애플리케이션 코드 및 데이터 파일 복사
COPY . .

# 앱이 실행될 포트 노출
EXPOSE 8000

# 애플리케이션 실행 명령어
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000", "--reload"]
