FROM openjdk:8-jdk-alpine
ADD Shanghai /etc/localtime
WORKDIR /app/
COPY target/jssh-1.0-SNAPSHOT.jar.jar /app/app.jar
COPY start-app.sh /usr/bin/start-app.sh
RUN chmod +x /usr/bin/start-app.sh  && echo "Asia/Shanghai" > /etc/timezone
EXPOSE 21051 27019
ENTRYPOINT /usr/bin/start-app.sh