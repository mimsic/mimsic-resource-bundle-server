FROM openjdk:17
MAINTAINER arz
EXPOSE 8080
ADD /target/mimsic-resource-bundle-server.jar /app/application.jar
ENTRYPOINT ["java", "-jar", "/app/application.jar"]