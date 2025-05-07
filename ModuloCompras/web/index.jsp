<%@page import="com.tims.modelo.Cliente"%>
<%
    Cliente cliente = (Cliente) session.getAttribute("cliente");
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Módulo de Compras</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    </head>
    <body>
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <div class="container-fluid">
                  <a class="navbar-brand" href="#">Tienda Testing</a>
                  <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                  </button>
                  <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                      <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="Controlador?accion=home">Home</a>
                      </li>
                      
                      <li class="nav-item">
                          <a class="nav-link" href="Controlador?accion=Carrito"><i class="fas fa-cart-plus">(<label style="color: darkorange">${contador}</label>)</i>Carrito</a>
                      </li>
                    </ul>
                    <form action="Controlador" method="get" class="d-flex">
                        <input type="hidden" name="accion" value="Buscar" />
                        <input class="form-control me-2" type="text" name="txtBuscar" placeholder="Buscar producto..." />
                        <button class="btn btn-success" type="submit">Buscar</button>
                    </form>
                    <c:if test="${not empty param.accion and param.accion eq 'Buscar' and empty productos}">
                        <script type="text/javascript">
                            alert('No se encontraron productos con ese nombre.');
                        </script>
                    </c:if>

                      <ul class="navbar-nav">
                            <c:choose>
                              <c:when test="${not empty cliente}">
                                <li class="nav-item dropdown">
                                  <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    <i class="fas fa-user"></i> ${cliente.getNombre()}
                                  </a>
                                  <ul class="dropdown-menu dropdown-menu-end">
                                    <li><a class="dropdown-item" href="Controlador?accion=Logout">Cerrar Sesión</a></li>
                                  </ul>
                                </li>
                              </c:when>
                              <c:otherwise>
                                <li class="nav-item">
                                  <a class="btn btn-outline-light ms-2" href="login.jsp">
                                    <i class="fas fa-user"></i> Iniciar Sesión
                                  </a>
                                </li>
                              </c:otherwise>
                            </c:choose>
                          </ul>
                  </div>
                </div>
            </nav>
        <div class="container mt-4">
            <div class="row">
                <c:forEach var="p" items="${productos}">
                    <div class="col-sm-4">
                        <div class="card">
                            <div class="card-header">
                                <label>${p.nombres}</label>
                            </div>
                            <div class="card-body">
                                <i>S/${p.getPrecio()}</i>
                                <img src="ControladorIMG?id=${p.getId()}" width="200" height="180">
                            </div>
                            <div class="card-footer text-center">
                                <label>${p.getDescripcion()}</label>
                                <div>
                                    <a href="Controlador?accion=AgregarCarrito&id=${p.getId()}" class="btn btn-outline-info">Agregar al Carrito</a>
                                    <a href="Controlador?accion=Comprar&id=${p.getId()}" class="btn btn-danger">Comprar</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
