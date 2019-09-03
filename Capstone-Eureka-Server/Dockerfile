FROM maven:3.5-jdk-8 AS build
COPY src /usr/src/eureka-app/src
COPY pom.xml usr/src/eureka-app
RUN mvn -f usr/src/eureka-app/pom.xml clean package

FROM gcr.io/distroless/java
ARG JAR_FILE=usr/src/eureka-app/target/eureka-1.0-SNAPSHOT.jar
COPY --from=build ${JAR_FILE} usr/eureka-app/eureka-1.0-SNAPSHOT.jar
WORKDIR usr/eureka-app/
ENTRYPOINT ["java","-jar","eureka-1.0-SNAPSHOT.jar"]
