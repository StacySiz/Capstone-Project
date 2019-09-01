FROM maven:3.5-jdk-8 AS build
COPY src /usr/src/notification-app/src
COPY pom.xml usr/src/notification-app
RUN mvn -f usr/src/notification-app/pom.xml clean package

FROM gcr.io/distroless/java
ARG JAR_FILE=usr/src/notification-app/target/capstone-email-1.0-SNAPSHOT.jar
COPY --from=build ${JAR_FILE} usr/notification-app/capstone-email-1.0-SNAPSHOT.jar
WORKDIR usr/notification-app/
ENTRYPOINT ["java","-jar","capstone-email-1.0-SNAPSHOT.jar"]
