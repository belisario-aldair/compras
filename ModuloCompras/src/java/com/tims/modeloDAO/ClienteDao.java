package com.tims.modeloDAO;

import com.tims.config.Conexion;
import com.tims.modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClienteDao {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public Cliente validar(String email, String password) {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE Email=? AND Password=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("idCliente"));
                cliente.setDni(rs.getString("Dni"));
                cliente.setNombre(rs.getString("Nombres"));
                cliente.setDireccion(rs.getString("Direccion"));
                cliente.setCorreo(rs.getString("Email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cliente;
    }
    
    public boolean registrar(Cliente cliente) {
        String sql = "INSERT INTO cliente (Dni, Nombres, Direccion, Email, Password) VALUES (?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getDni());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getDireccion());
            ps.setString(4, cliente.getCorreo());
            ps.setString(5, cliente.getPassword());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
