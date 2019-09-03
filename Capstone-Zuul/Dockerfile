FROM maven:3.5-jdk-8 AS build
COPY src /usr/src/zuul-app/src
COPY pom.xml usr/src/zuul-app
RUN mvn -f usr/src/zuul-app/pom.xml clean package

FROM gcr.io/distroless/java
ARG JAR_FILE=usr/src/zuul-app/target/zuul-1.0-SNAPSHOT.jar
COPY --from=build ${JAR_FILE} usr/zuul-app/zuul-1.0-SNAPSHOT.jar
WORKDIR usr/zuul-app/
ENTRYPOINT ["java","-jar","zuul-1.0-SNAPSHOT.jar"]
