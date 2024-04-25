FROM openjdk:17

ENV TZ=Asia/Seoul

COPY build/libs/concert-0.0.1-SNAPSHOT.jar /app/concert-0.0.1-SNAPSHOT.jar

EXPOSE 8000

ENTRYPOINT java \
  -jar /app/concert-0.0.1-SNAPSHOT.jar \
  --spring.profiles.active=dev \