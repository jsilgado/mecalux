FROM openjdk:16-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/warehouse-rest-1.0.0.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]