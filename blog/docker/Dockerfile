FROM openjdk:11-jre
ARG JAR_FILE=build/libs/*.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
