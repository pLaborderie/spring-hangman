FROM eclipse-temurin:21 AS build
RUN mkdir /app
COPY . /app
WORKDIR /app
RUN chmod +x ./mvnw
RUN ./mvnw clean install

FROM eclipse-temurin:21
ENV LANGUAGE='en_US:en'

COPY --chown=185 --from=build /app/target/*.jar /deployments/run-app.jar

EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "/deployments/run-app.jar" ]