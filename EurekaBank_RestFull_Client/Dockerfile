FROM tomcat:9.0-jdk11

# Elimina apps por defecto si quieres
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia tu WAR generado
COPY dist/eurekabank_restfull_client_java.war /usr/local/tomcat/webapps/ROOT.war

# Expone el puerto 8080
EXPOSE 8080
