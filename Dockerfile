FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENV VERSION=1.0
CMD ["java", "-jar", "app.jar"]
