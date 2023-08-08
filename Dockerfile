FROM openjdk:17 AS build
WORKDIR /app
COPY . /app/
RUN microdnf install findutils
RUN ./gradlew build
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/build/libs/backendchallenger-0.0.1-SNAPSHOT.jar poke-api.jar
EXPOSE 8080
CMD ["java", "-jar", "poke-api.jar"]