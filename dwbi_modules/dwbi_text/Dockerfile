FROM openjdk:8
VOLUME /tmp
COPY target/*.jar dwbi_text.jar
ENV PORT=9096
ENTRYPOINT ["java","-jar","-Xms128m","-Xmx256m","dwbi_text.jar","--spring.profiles.active=prod"]
EXPOSE $PORT