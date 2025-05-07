<%@page import="com.tims.modelo.Cliente"%>
<%
    Cliente cliente = (Cliente) session.getAttribute("cliente");
    if (cliente == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
                          <a class="nav-link" href="Controlador?accion=home">Seguir Comprando</a>
                      </li>
                    </ul>
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
            <h3>Carrito</h3>
            <br>
            <div class="row">
                <div class="col-sm-8">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>ITEM</th>
                                <th>NOMBRE</th>
                                <th>DESCRIPCIÓN</th>
                                <th>PRECIO</th>
                                <th>CANT</th>
                                <th>SUBTOTAL</th>
                                <th>ACCION</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="car" items="${carrito}">
                                <tr>
                                    <td>${car.getItem()}</td>
                                    <td>${car.getNombres()}</td>
                                    <td>${car.getDescripcion()}
                                        <img src="ControladorIMG?id=${car.getIdProducto()}" width="100" height="100">
                                    </td>
                                    <td>${car.getPrecioCompra()}</td>
                                    <td>
                                        <input type="hidden" id="idpro" value="${car.getIdProducto()}">
                                        <input type="number" id="Cantidad" value="${car.getCantidad()}" class="form-control text-center" min="1">
                                    </td>
                                    <td>${car.getSubTotal()}</td>
                                    <td>
                                        <input type="hidden" id="idp" value="${car.getIdProducto()}">
                                        <a href="#" id="btnDelete">eliminar</a>
                                    </td> 
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="col-sm-4">
                    <div class="card">
                        <div class="card-header">
                            <h3>Generar Compra</h3>
                        </div>
                        <div class="card-body">
                            <label>Subtotal:</label>
                                <input type="text" value="S/.${totalPagar}" readonly class="form-control">
                                <label>Descuento:</label>
                                <input type="text" value="S/.0.00" readonly class="form-control">
                                <label>Total a Pagar:</label>
                                <input type="text" value="S/.${totalPagar}" readonly class="form-control">
                        </div>
                        <div class="card-footer">
                            <a href="" class="btn btn-info btn-block">Realizar Pago</a>
                            <a class="btn btn-danger btn-block">Generar Compra</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
            <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
            <script src="js/funciones.js" type="text/javascript"></script>
            <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
