FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/stats-server-*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java","-jar","/app/app.jar"]