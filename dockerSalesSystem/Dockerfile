FROM eclipse-temurin:21-jdk

WORKDIR /appSalesSystem

COPY ../target/salesSystem-0.0.1-SNAPSHOT.jar app.jar

RUN mkdir -p /appSalesSystem/salesSystemLOGS

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]