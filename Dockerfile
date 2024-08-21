FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /REProject

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /REProject

COPY --from=build /REProject/target/REProject-0.0.1-SNAPSHOT.jar REProject-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "REProject-0.0.1-SNAPSHOT.jar"]
