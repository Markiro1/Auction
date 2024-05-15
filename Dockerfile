FROM maven:3.6.3-openjdk-17 as builder

WORKDIR /opt/app
COPY mvnw pom.xml ./
COPY ./src ./src
RUN mvn clean install -DskipTests

FROM openjdk:17
WORKDIR /opt/app
EXPOSE 8090
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]