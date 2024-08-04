# Sử dụng image base OpenJDK
FROM openjdk:17-jdk-alpine

# Sao chép JAR file từ target vào image
COPY target/openlab_be-0.0.1-SNAPSHOT.jar /openlab_be.jar

# Sao chép file cấu hình ứng dụng
COPY src/main/resources/application.properties /openlab_be/application.properties

# Chỉ định lệnh khởi động ứng dụng
ENTRYPOINT ["java", "-jar", "/openlab_be.jar"]
