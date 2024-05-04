FROM openjdk:17-oracle

ENV TZ=Asia/Seoul

# 앱을 위한 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
ARG JAR_FILE="./build/libs/*.jar"
COPY ${JAR_FILE} eatceed.jar

# CSV 파일 복사
COPY ./food_data.csv ./food_data.csv

EXPOSE 8080

# CMD 명령어에서 CSV 파일의 위치를 절대 경로로 지정
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "eatceed.jar", "./food_data.csv"]
