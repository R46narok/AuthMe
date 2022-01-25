FROM openjdk:17-alpine
COPY . .
RUN ./gradlew build
ENTRYPOINT [ "java", "-jar", "./build/libs/authme-0.0.1-SNAPSHOT.jar" ]