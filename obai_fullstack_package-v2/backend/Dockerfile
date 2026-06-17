FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app
ENV TZ=Asia/Shanghai
COPY --from=build /workspace/target/obai-platform-1.0.0.jar /app/obai-platform.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/obai-platform.jar"]
