FROM tomcat:jre17
RUN rm -rf /usr/local/tomcat/webapps/*
COPY build/libs/summarization-server-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh", "run"]
