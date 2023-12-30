# Use the official OpenJDK 17 image as the base image
FROM openjdk:17
EXPOSE 8080
ADD target/spring-boot-docker.jar spring-boot-docker.jar
ENTRYPOINT [ "java","-jar","/spring-boot-docker.jar"]