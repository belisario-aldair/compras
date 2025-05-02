package com.tims.controlador;

import com.tims.config.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestConexionServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            Conexion conexion = new Conexion();
            Connection con = conexion.getConnection(); // Este ahora puede lanzar SQLException
            
            if (con != null) {
                out.println("<html><body><h2>Conexión exitosa a la base de datos</h2></body></html>");
            } else {
                out.println("<html><body><h2>Error en la conexión a la base de datos</h2></body></html>");
            }
        } catch (SQLException e) {
            // Si ocurre una excepción SQL, mostramos un mensaje de error adecuado.
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            try (PrintWriter out = response.getWriter()) {
                out.println("<html><body><h2>Error de conexión a la base de datos: " + e.getMessage() + "</h2></body></html>");
                e.printStackTrace();  // Para debuggear en el servidor
            }
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada.
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
            try (PrintWriter out = response.getWriter()) {
                out.println("<html><body><h2>Error inesperado: " + e.getMessage() + "</h2></body></html>");
                e.printStackTrace();  // Para debuggear en el servidor
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
