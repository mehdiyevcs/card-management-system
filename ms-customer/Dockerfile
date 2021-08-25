FROM openjdk:11
ARG JAR_FILE=build/libs/ms-customer-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} ms-customer.jar
ENTRYPOINT ["java","-jar","/app.jar"]