FROM openjdk:11
EXPOSE 8120

ADD target/auto_check_service-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]