# Sử dụng OpenJDK 8 làm image cơ sở
FROM openjdk:17-jdk-alpine

# Thêm tệp JAR vào trong image
COPY target/openlab_be-0.0.1-SNAPSHOT.jar /openlab_be.jar
COPY src/main/resources/application.properties /openlab_be/application.properties


ENTRYPOINT ["java","-jar","/openlab_be.jar"]