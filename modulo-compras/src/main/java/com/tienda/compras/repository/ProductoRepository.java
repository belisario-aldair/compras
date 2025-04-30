package com.tienda.compras.repository;

import com.tienda.compras.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void crearProducto(String nombre, double precio, int stock) {
        jdbcTemplate.update("CALL sp_crear_producto(?, ?, ?)", nombre, precio, stock);
    }

    public List<Producto> listarProductos() {
        return jdbcTemplate.query("CALL sp_listar_productos()", new ProductoMapper());
    }

    public Producto obtenerProducto(Long id) {
        return jdbcTemplate.queryForObject("CALL sp_obtener_producto(?)", new ProductoMapper(), id);
    }

    public void actualizarProducto(Long id, String nombre, double precio, int stock) {
        jdbcTemplate.update("CALL sp_actualizar_producto(?, ?, ?, ?)", id, nombre, precio, stock);
    }

    public void eliminarProducto(Long id) {
        jdbcTemplate.update("CALL sp_eliminar_producto(?)", id);
    }

    private static class ProductoMapper implements RowMapper<Producto> {
        @Override
        public Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Producto(
                    rs.getLong("id"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getInt("stock")
            );
        }
    }
}
