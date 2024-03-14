FROM openjdk:8
VOLUME /tmp
COPY target/*.jar dwbi_chart.jar
ENV PORT=9095
ENTRYPOINT ["java","-jar","-Xms128m","-Xmx256m","dwbi_chart.jar","--spring.profiles.active=prod"]
EXPOSE $PORT