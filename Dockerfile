FROM maven:3.8.6-openjdk-18 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

FROM openjdk:18-jre-slim
COPY --from=build /home/app/target/waiterapp.jar /usr/local/lib/waiterapp.jar
COPY src /home/app/src
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/waiterapp.jar"]