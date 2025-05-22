package com.tims.modeloDAO;

import com.tims.config.Conexion;
import com.tims.modelo.Producto;
import com.tims.validación.Validaciones;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public class ProductoDAO {
    Conexion cn = new Conexion();
    Validaciones validaciones = new Validaciones();

    public Producto listarId(int id) {
        String sql = "SELECT * FROM producto WHERE idProducto = ?";
        Producto p = new Producto();
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                    p.setNombres(rs.getString(2));
                    p.setFoto(rs.getBinaryStream(3));
                    p.setDescripcion(rs.getString(4));
                    p.setPrecio(rs.getDouble(5));
                    p.setStock(rs.getInt(6));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombres(rs.getString(2));
                p.setFoto(rs.getBinaryStream(3));
                p.setDescripcion(rs.getString(4));
                p.setPrecio(rs.getDouble(5));
                p.setStock(rs.getInt(6));
                productos.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }

    public List<Producto> buscar(String nombre) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE nombres LIKE ?";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto();
                    p.setId(rs.getInt(1));
                    p.setNombres(rs.getString(2));
                    p.setFoto(rs.getBinaryStream(3));
                    p.setDescripcion(rs.getString(4));
                    p.setPrecio(rs.getDouble(5));
                    p.setStock(rs.getInt(6));
                    productos.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }

    public void listarImg(int id, HttpServletResponse response) {
        String sql = "SELECT * FROM producto WHERE idProducto = ?";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    try (InputStream inputStream = rs.getBinaryStream("Foto");
                         OutputStream outputStream = response.getOutputStream();
                         BufferedInputStream bis = new BufferedInputStream(inputStream);
                         BufferedOutputStream bos = new BufferedOutputStream(outputStream)) {

                        int i;
                        while ((i = bis.read()) != -1) {
                            bos.write(i);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean registrar(Producto producto) {
        if (producto == null) return false;

        String nombre = producto.getNombres() != null ? producto.getNombres().trim() : "";
        Integer stock = producto.getStock();
        Double precio = producto.getPrecio();

        if (nombre.isEmpty()) {
            System.out.println("❌ El nombre del producto es obligatorio.");
            return false;
        }
        if (!validaciones.stockValido(stock)) {
            System.out.println("❌ Stock inválido (debe ser >= 0).");
            return false;
        }
        if (!validaciones.subtotalValido(precio)) {
            System.out.println("❌ Precio inválido (debe ser >= 0).");
            return false;
        }

        String sql = "INSERT INTO producto (nombres, foto, descripcion, precio, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setBinaryStream(2, producto.getFoto());
            ps.setString(3, producto.getDescripcion() != null ? producto.getDescripcion().trim() : "");
            ps.setDouble(4, precio);
            ps.setInt(5, stock);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
