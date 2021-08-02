FROM adoptopenjdk/openjdk13
EXPOSE 8080
WORKDIR /apps
#RUN apt-get update && apt-get install
COPY /target/external-1.0.0-SNAPSHOT.jar  /apps/app.jar
ENTRYPOINT ["java", "-jar", "/apps/app.jar"]
