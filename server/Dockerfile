FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY target/AIDE-1.0.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
