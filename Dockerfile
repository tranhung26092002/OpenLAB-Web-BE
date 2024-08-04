# Sử dụng image base OpenJDK
FROM openjdk:17-jdk-alpine

# Tạo thư mục ứng dụng
WORKDIR /app

# Sao chép JAR file từ target vào image
COPY target/openlab_be-0.0.1-SNAPSHOT.jar /app/openlab_be.jar

# Chỉ định lệnh khởi động ứng dụng
ENTRYPOINT ["java", "-jar", "/app/openlab_be.jar"]
