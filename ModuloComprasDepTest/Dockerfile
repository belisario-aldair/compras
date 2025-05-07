FROM glassfish/server:5.0.1-jdk8

COPY ModuloCompras.war /glassfish5/glassfish/domains/domain1/autodeploy/

EXPOSE 8080

CMD ["/glassfish5/glassfish/bin/asadmin", "start-domain", "--verbose"]
