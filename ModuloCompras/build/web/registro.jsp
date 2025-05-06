<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Registro</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
            <div class="card shadow p-4" style="width: 100%; max-width: 450px;">
                <div class="text-center mb-3">
                    <h4><i class="fas fa-user-plus"></i> Crear una Cuenta</h4>
                </div>
                <form action="Controlador?accion=Registrar" method="post">
                    <div class="mb-3">
                        <label for="dni" class="form-label">DNI</label>
                        <input type="text" class="form-control" name="txtDni" id="dni" required>
                    </div>
                    <div class="mb-3">
                        <label for="nombres" class="form-label">Nombres completos</label>
                        <input type="text" class="form-control" name="txtNombres" id="nombres" required>
                    </div>
                    <div class="mb-3">
                        <label for="direccion" class="form-label">Dirección</label>
                        <input type="text" class="form-control" name="txtDireccion" id="direccion" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Correo electrónico</label>
                        <input type="email" class="form-control" name="txtEmail" id="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="pass" class="form-label">Contraseña</label>
                        <input type="password" class="form-control" name="txtPassword" id="pass" required>
                    </div>
                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary">Registrarse</button>
                    </div>
                    <div class="mt-3 text-danger text-center">
                        <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
                    </div>
                    <div class="mt-3 text-center">
                        <span>¿Ya tienes cuenta? <a href="login.jsp">Inicia sesión</a></span>
                    </div>
                </form>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
