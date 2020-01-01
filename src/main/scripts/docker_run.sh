#!/usr/bin/env sh

JAR="blog-backend.jar"
IMAGE="blog-backend:1.1.0-SNAPSHOT"

echo "Compiling jar as ${JAR}"
sh gradlew shadowJar

echo "Building ${IMAGE} docker image"
docker build . -t ${IMAGE}

echo "Running ${IMAGE}"
docker run -p 8080:8080 ${IMAGE}
