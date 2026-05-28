FROM azul/zulu-openjdk:21-jre-headless
COPY target/stateless-auth-api-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]