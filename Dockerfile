FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java","-Dspring.profiles.active=${linux}", "-Dspring.config.location=classpath:/config.properties","-jar","app.jar"]
