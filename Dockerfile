FROM openjdk:11.0.5-jre-stretch

ENV APP_HOME /app
CMD mkdir ${APP_HOME}

COPY build/libs/blog-backend-1.1.0-SNAPSHOT-all.jar ${APP_HOME}/blog-backend.jar

WORKDIR ${APP_HOME}

CMD ["java", "-jar", "blog-backend.jar"]
