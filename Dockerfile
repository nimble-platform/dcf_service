FROM tomcat
MAINTAINER andrea.musumeci@holonix.it

RUN rm -rf /usr/local/tomcat/webapps/*
ADD ./target/dcf_service.war /usr/local/tomcat/webapps/ROOT.war
COPY ./WEB-INF/web.xml /usr/local/tomcat/conf/web.xml

CMD ["catalina.sh", "run"]
