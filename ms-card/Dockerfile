FROM openjdk:11
ARG JAR_FILE=build/libs/ms-card-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ms-card.jar
ENTRYPOINT ["java","-jar","/app.jar"]