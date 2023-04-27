FROM tomcat:9.0-jdk11-openjdk
COPY target/user-auth-service.war /usr/local/tomcat/webapps/
EXPOSE 8080