#!/bin/bash

# build jar
./gradlew clean build

export SERVER_PORT=8080
export ROOT_LOGGING_LEVEL=INFO
export LOGGING_LEVEL=DEBUG

if [ "$1" == "--refresh" ]; then
  REFRESH="--refresh-dependencies"
fi

if [ "$1" != "--fast" ]; then
    echo "--- REBUILD --- "$REFRESH
    rm ./build/libs/quasar*-all.jar
    # force refresh russ libs
    ./gradlew $REFRESH clean build
fi


#cp -r ./build/classes/java/main/META-INF/swagger ../docs

java -jar -Dmicronaut.environments=dev -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5013 ./build/libs/quasar*-all.jar

