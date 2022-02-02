FROM openjdk:11-slim AS build

WORKDIR /home/src
LABEL stage=builder name=quasar

# Copy src
COPY . /home/src

# Build
RUN ./gradlew clean build -x test --no-daemon


###############################################################################
# Release image
#
FROM openjdk:11-jre-slim

LABEL stage=release name=quasar maintainer="Fernando Garcia"

RUN mkdir /app

EXPOSE 8080
ENV SERVER_PORT=8080 \
    ROOT_LOGGING_LEVEL=INFO \
    LOGGING_LEVEL=DEBUG

COPY --from=build /home/src/build/libs/quasar*-all.jar /app/quasar.jar

ENTRYPOINT ["java", "-XX:+UseContainerSupport","-jar","/app/quasar.jar"]
