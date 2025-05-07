FROM oracle/glassfish:4.1

COPY ModuloCompras.war /glassfish/domains/domain1/autodeploy/

EXPOSE 8080

CMD ["/glassfish/bin/asadmin", "start-domain", "--verbose"]
