FROM gradle:jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

FROM adoptopenjdk/openjdk14:ubi

ENV HOME=/home/app
ENV APP_HOME=$HOME/messenger

RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME

COPY --from=build /home/gradle/src/build/libs/messenger-0.0.1-SNAPSHOT.jar $APP_HOME/messenger.jar

EXPOSE 8080

CMD ["java", "-jar", "$APP_HOME/messenger.jar"]
