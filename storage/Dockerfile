FROM nexus.post.kz:8200/docker-images/jdk21-maven-net-tools:latest as build
WORKDIR /app
COPY . /app
ARG PROJECT_NAME=Storage
COPY settings.xml /root/.m2/settings.xml
COPY pom.xml pom.xml
RUN ["mvn", "compile", "package", "-DskipTests"]
RUN ls -ls ./*

FROM nexus.post.kz:8200/docker-images/jdk21-slim:latest  as prod
WORKDIR /app
RUN groupadd -r nonroot && useradd -r -g nonroot nonroot
COPY --from=build /app/target/*.jar app.jar
RUN chown -R nonroot:nonroot /app && \
    chmod 755 /app && \
    chmod 644 /app/*.jar
RUN ln -snf /usr/share/zoneinfo/Asia/Almaty /etc/localtime && echo Asia/Almaty > /etc/timezone
USER nonroot
EXPOSE 8080
ENTRYPOINT ["java", "-Duser.timezone=Asia/Almaty", "-jar", "app.jar"]
