FROM maven:3.5-jdk-8 AS build
COPY src /usr/src/app/src
COPY pom.xml usr/src/app
RUN mvn -f usr/src/app/pom.xml clean package

FROM gcr.io/distroless/java
ARG JAR_FILE=usr/src/app/target/web-stand-up-1.0-SNAPSHOT.jar
COPY --from=build ${JAR_FILE} usr/app/web-stand-up-1.0-SNAPSHOT.jar
WORKDIR usr/app/
EXPOSE 80
ENTRYPOINT ["java","-jar","web-stand-up-1.0-SNAPSHOT.jar"]
