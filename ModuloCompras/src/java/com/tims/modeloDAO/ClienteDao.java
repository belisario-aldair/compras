package com.tims.modeloDAO;

import com.tims.config.Conexion;
import com.tims.modelo.Cliente;
import com.tims.validación.Validaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClienteDao {
    Conexion cn = new Conexion();
    Validaciones validaciones = new Validaciones();

    public Cliente validar(String email, String password) {
        if (email == null || password == null) return null;

        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE LOWER(Email) = LOWER(?) AND Password = ?";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email.trim());
            ps.setString(2, password.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("idCliente"));
                    cliente.setDni(rs.getString("Dni"));
                    cliente.setNombre(rs.getString("Nombres"));
                    cliente.setDireccion(rs.getString("Direccion"));
                    cliente.setCorreo(rs.getString("Email"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cliente;
    }

    public boolean registrar(Cliente cliente) {
        if (cliente == null) return false;

        String dni = validaciones.normalizarTexto(cliente.getDni());
        String email = cliente.getCorreo() != null ? cliente.getCorreo().trim().toLowerCase() : "";

        if (dni.isEmpty() || email.isEmpty() ||
            cliente.getNombre() == null || cliente.getNombre().trim().isEmpty() ||
            cliente.getPassword() == null || cliente.getPassword().trim().isEmpty()) {
            System.out.println("❌ Datos obligatorios incompletos.");
            return false;
        }

        if (validaciones.existeDni(dni)) {
            System.out.println("❌ El DNI ya está registrado.");
            return false;
        }

        if (validaciones.existeEmail(email)) {
            System.out.println("❌ El correo ya está registrado.");
            return false;
        }

        String sql = "INSERT INTO cliente (Dni, Nombres, Direccion, Email, Password) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dni);
            ps.setString(2, cliente.getNombre().trim());
            ps.setString(3, cliente.getDireccion() != null ? cliente.getDireccion().trim() : "");
            ps.setString(4, email);
            ps.setString(5, cliente.getPassword().trim());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
