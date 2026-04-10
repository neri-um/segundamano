FROM maven:3.8.5-jdk-11 AS builder
WORKDIR /app/
COPY pom.xml .
RUN mvn -e -B dependency:resolve
COPY src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:11
COPY --from=builder /app/target/productos.spring-0.0.1-SNAPSHOT.war /app.war
CMD ["java", "-jar", "/app.war"]
EXPOSE 9090
