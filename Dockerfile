FROM amazoncorretto:17
LABEL authors="zyona"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} TesteCliniconnect-0.0.1-SNAPSHOT.jar
CMD apt-get update -y
ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/TesteCliniconnect-0.0.1-SNAPSHOT.jar"]