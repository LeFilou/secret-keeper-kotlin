# Dependencies
FROM gradle:7.6.0-jdk17-alpine AS build
COPY --chown=gradle:gradle . /workspace
WORKDIR /workspace
RUN gradle build -s -x test -x integrationTest

FROM eclipse-temurin:17-alpine

RUN mkdir /app

RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
RUN chown -R javauser:javauser /app
USER javauser

WORKDIR /app

COPY --from=build /workspace/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]