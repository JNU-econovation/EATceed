FROM openjdk:17-oracle

ENV TZ=Asia/Seoul

ARG JAR_FILE="./build/libs/*.jar"
COPY ${JAR_FILE} eatceed.jar

EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "eatceed.jar"]
