FROM openjdk:11
ARG JAR_FILE=build/libs/ms-auth-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ms-auth.jar
ENTRYPOINT ["java","-jar","/app.jar"]