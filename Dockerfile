# Sử dụng image OpenJDK để chạy ứng dụng
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Sao chép JAR file từ image build vào image chạy
COPY --from=build /app/target/openlab_be-0.0.1-SNAPSHOT.jar /app/openlab_be.jar

# Sao chép file cấu hình ứng dụng
COPY src/main/resources/application.properties /app/application.properties

# Chỉ định lệnh khởi động ứng dụng
ENTRYPOINT ["java", "-jar", "/app/openlab_be.jar"]
