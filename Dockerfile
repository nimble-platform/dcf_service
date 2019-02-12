FROM tomcat
MAINTAINER andrea.musumeci@holonix.it

RUN rm -rf /usr/local/tomcat/webapps/*

ADD ./target/dcf-service.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]