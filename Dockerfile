FROM openjdk:17-jdk-slim

COPY ./build/libs/test-assignment-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 8080

CMD ["java", "-Xmx2048m", "-jar", "/app.jar", "--server.port=8080"]