FROM maven:3.8.5-jdk-11 AS builder
WORKDIR /app/
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:11
COPY --from=builder /app/target/compraventas-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
EXPOSE 8083