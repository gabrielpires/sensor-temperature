FROM maven:3.8.2-openjdk-16-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true

FROM openjdk:16-jdk-slim
COPY --from=build /home/app/target/sensor-1.0-SNAPSHOT.jar /usr/local/lib/sensor.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/sensor.jar"]