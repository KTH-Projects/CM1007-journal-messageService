FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
<<<<<<< HEAD
=======
#USER root
>>>>>>> 67c50bf (added unit tests and github actions)
RUN chmod +x ./mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src

<<<<<<< HEAD
=======
EXPOSE 8082

>>>>>>> 67c50bf (added unit tests and github actions)
CMD ["./mvnw", "spring-boot:run"]