FROM gradle:7.5.1 AS builder
WORKDIR /api
COPY src /api/src

COPY build.gradle /api/
COPY settings.gradle /api/
RUN gradle build


FROM openjdk:17
WORKDIR /api
COPY --from=builder /api/build/libs/FAQ-API-1.0.jar FAQ-API-1.0.jar
RUN java -jar FAQ-API-1.0.jar

