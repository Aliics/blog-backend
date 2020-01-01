FROM openjdk:11.0.5-jre-slim

ENV APP_HOME /app
CMD mkdir ${APP_HOME}

COPY build/libs/blog-backend.jar ${APP_HOME}/blog-backend.jar

WORKDIR ${APP_HOME}

CMD ["java", "-jar", "blog-backend.jar"]
