FROM openjdk:13-alpine


RUN mkdir /app


WORKDIR /app


COPY ./target/*.jar app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]