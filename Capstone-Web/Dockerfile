FROM maven:3.5-jdk-8 AS build
COPY src /usr/src/web-app/src
COPY pom.xml usr/src/web-app
RUN mvn -f usr/src/web-app/pom.xml clean package

FROM gcr.io/distroless/java
ARG JAR_FILE=usr/src/web-app/target/web-stand-up-1.0-SNAPSHOT.jar
COPY --from=build ${JAR_FILE} usr/web-app/web-stand-up-1.0-SNAPSHOT.jar
WORKDIR usr/web-app/
ENTRYPOINT ["java","-jar","web-stand-up-1.0-SNAPSHOT.jar"]
