FROM openjdk:21-jdk-slim as build
WORKDIR /app
COPY . /app
ENV MAVEN_VERSION 3.8.1
RUN apt-get update; \
    apt-get install -y maven wget;
RUN ln -snf /usr/share/zoneinfo/Asia/Oral /etc/localtime && echo Asia/Oral > /etc/timezone

ARG PROJECT_NAME=AdminEureka
COPY pom.xml pom.xml

RUN ["mvn", "compile", "package","-DskipTests"]

RUN ls -ls ./*

FROM openjdk:21-jdk-slim
RUN apt-get update; \
    apt-get install -y curl wget;
WORKDIR /app
RUN groupadd -r nonroot && useradd -r -g nonroot nonroot

ARG PROJECT_NAME=AdminEureka
RUN chown -R nonroot:nonroot /app
COPY --from=build  /app/target/*.jar app.jar
RUN chmod 755 /app
RUN chmod 644 /app/*.jar
RUN ln -snf /usr/share/zoneinfo/Asia/Oral /etc/localtime && echo Asia/Oral > /etc/timezone

USER nonroot

EXPOSE 8080
ENTRYPOINT ["java", "-Duser.timezone=Asia/Oral", "-jar", "app.jar"]
