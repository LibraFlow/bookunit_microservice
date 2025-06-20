FROM gradle:8.6.0-jdk21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew bootJar

FROM openjdk:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/app/build/libs/*.jar app.jar

ENV PORT=8085
ENV SPRING_PROFILES_ACTIVE=prod
ENV INSTANCE_CONNECTION_NAME=libraflow-457510:europe-west4:bookunit-service-db
ENV SPRING_DATASOURCE_USERNAME=user4
ENV SPRING_DATASOURCE_PASSWORD=password4
ENV SPRING_DATASOURCE_DATABASE=db4

EXPOSE 8085

CMD ["java", "-Xmx512m", "-Xms256m", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"] 