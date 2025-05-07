FROM glassfish:4.1.1-jdk8

COPY ModuloCompras.war /opt/glassfish4/glassfish/domains/domain1/autodeploy/

EXPOSE 8080

CMD ["asadmin", "start-domain", "-v"]
