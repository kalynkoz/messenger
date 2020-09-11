FROM adoptopenjdk/openjdk14:ubi

ENV HOME=/home/app
ENV APP_HOME=$HOME/messenger

RUN mkdir -p $APP_HOME
WORKDIR $APP_HOME

COPY build/libs/messenger-0.0.1-SNAPSHOT.jar $APP_HOME/messenger.jar

EXPOSE 8080

CMD ["java", "-jar", "$APP_HOME/messenger.jar"]
