FROM openjdk:8
VOLUME /tmp
COPY target/*.jar dwbi_gateway.jar
ENV PORT=8099
ENTRYPOINT ["java","-jar","-Xms128m","-Xmx256m","dwbi_gateway.jar","--spring.profiles.active=prod"]
EXPOSE $PORT