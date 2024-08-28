    # Sử dụng image base OpenJDK
    FROM openjdk:17-jdk-alpine

    # Sao chép JAR file từ target vào image
    COPY target/*.jar /openlab_be.jar

    # Chỉ định lệnh khởi động ứng dụng
    ENTRYPOINT ["java", "-jar", "/openlab_be.jar"]
