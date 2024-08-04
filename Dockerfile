# Sử dụng image Maven để build ứng dụng
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Sử dụng image OpenJDK để chạy ứng dụng
FROM openjdk:17-jre-slim
WORKDIR /app
COPY --from=build /app/target/*.jar ./openlab_be.jar
ENTRYPOINT ["java", "-jar", "openlab_be.jar"]
