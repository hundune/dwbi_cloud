FROM openjdk:8
VOLUME /tmp
COPY target/*.jar dwbi_service.jar
ENV PORT=9094
ENTRYPOINT ["java","-jar","-Xms128m","-Xmx256m","dwbi_service.jar","--spring.profiles.active=prod"]
EXPOSE $PORT