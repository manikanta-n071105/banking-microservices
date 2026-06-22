FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

ENTRYPOINT ["java","-jar","target/config-server-0.0.1-SNAPSHOT.jar"]