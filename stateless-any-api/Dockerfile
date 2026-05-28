FROM azul/zulu-openjdk:21-jre-headless
COPY target/stateless-any-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]