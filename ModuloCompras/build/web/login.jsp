<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Iniciar Sesión</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
            <div class="card shadow p-4" style="width: 100%; max-width: 400px;">
                <div class="text-center mb-3">
                    <h4><i class="fas fa-user-circle"></i> Iniciar Sesión</h4>
                </div>
                <form action="Controlador?accion=Login" method="post">
                    <div class="mb-3">
                        <label for="email" class="form-label">Correo electrónico</label>
                        <input type="email" class="form-control" name="txtEmail" id="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="pass" class="form-label">Contraseña</label>
                        <input type="password" class="form-control" name="txtPassword" id="pass" required>
                    </div>
                    <div class="d-grid">
                        <button type="submit" class="btn btn-primary">Iniciar Sesión</button>
                    </div>
                    <div class="mt-3 text-danger text-center">
                        <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>
                    </div>
                    <div class="mt-3 text-center">
                        <span>¿No tienes cuenta? <a href="registro.jsp">Regístrate aquí</a></span>
                    </div>
                </form>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
