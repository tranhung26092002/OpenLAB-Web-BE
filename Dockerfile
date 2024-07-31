# Sử dụng OpenJDK 8 làm image cơ sở
FROM openjdk:17-jdk-alpine

# Thêm tệp JAR vào trong image
ADD target/openlab_be.jar openlab_be.jar

# Mở cổng 8080 để truy cập ứng dụng
EXPOSE 8082

# Chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "/openlab_be.jar"]
