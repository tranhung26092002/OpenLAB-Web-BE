# Sử dụng OpenJDK 8 làm image cơ sở
FROM openjdk:17-jdk-alpine

WORKDIR /app

# Thêm tệp JAR vào trong image
COPY target/openlab_be-0.0.1-SNAPSHOT.jar /app.jar

# Mở cổng 8080 để truy cập ứng dụng
EXPOSE 8082

ENTRYPOINT ["java","-jar","/app.jar"]