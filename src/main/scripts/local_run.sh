#!/usr/bin/env sh

JAR="blog-backend.jar"

echo "Compiling jar as ${JAR}"
sh gradlew shadowJar

echo "Running ${JAR}"
java -jar build/libs/${JAR}
