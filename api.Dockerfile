FROM openjdk:17-alpine

RUN apk add --no-cache bash python3 py3-pip

COPY . /app
WORKDIR /app

RUN chmod +x gradlew

ENV LOGIN=default_login
ENV PASSWORD=default_password
ENV SPRING_PROFILES_ACTIVE=docker
ENV TESTCONTAINERS_CHECKS_DISABLE=true

EXPOSE 5555

CMD ["./gradlew", "api-tests:clean", "api-tests:test", "api-tests:serveReports"]
