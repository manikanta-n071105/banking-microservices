FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

ENTRYPOINT ["java","-jar","target/service-registry-0.0.1-SNAPSHOT.jar"]