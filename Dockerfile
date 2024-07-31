FROM openjdk:17-oracle
VOLUME /tmp

ADD build/libs/backend-1.0.0.jar app.jar
RUN bash -c 'touch /app.jar'

ENV TZ=Asia/Seoul

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]