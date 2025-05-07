FROM glassfish:latest

COPY ModuloCompras.war /opt/glassfish5/glassfish/domains/domain1/autodeploy/

EXPOSE 8080

CMD ["/opt/glassfish5/glassfish/bin/asadmin", "start-domain", "--verbose", "domain1"]
