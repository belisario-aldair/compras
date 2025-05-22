package com.tims.validación;

import com.tims.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.Normalizer;

public class Validaciones {
    Conexion cn = new Conexion();

    // Evita duplicados por correo (insensible a mayúsculas)
    public boolean existeEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;

        String sql = "SELECT 1 FROM cliente WHERE LOWER(Email) = LOWER(?)";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // si hay un resultado, ya existe
            }

        } catch (Exception e) {
            e.printStackTrace();
            return true; // en caso de error, mejor prevenir la inserción
        }
    }

    // Evita duplicados por DNI (insensible a mayúsculas)
    public boolean existeDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) return false;

        String sql = "SELECT 1 FROM cliente WHERE LOWER(Dni) = LOWER(?)";
        try (Connection con = cn.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dni.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // si hay un resultado, ya existe
            }

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    // Stock válido solo si es número no negativo
    public boolean stockValido(Integer stock) {
        return stock != null && stock >= 0;
    }

    // Subtotal válido solo si es número no negativo
    public boolean subtotalValido(Double subtotal) {
        return subtotal != null && subtotal >= 0;
    }

    // Normaliza texto: elimina tildes y convierte a minúsculas
    // PERO conserva la letra "ñ"
    public String normalizarTexto(String texto) {
        if (texto == null) return "";

        // Reemplaza las tildes pero conserva "ñ"
        String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
            .replaceAll("(?i)\\p{InCombiningDiacriticalMarks}+", "")  // elimina tildes
            .replaceAll("[\\p{M}]", "")  // seguridad extra
            .replace("Ñ", "Ñ").replace("ñ", "ñ"); // restaura ñ

        return normalizado.toLowerCase();
    }

    // Compara dos textos ignorando tildes, mayúsculas y espacios extra
    public boolean textoEquivalente(String a, String b) {
        return normalizarTexto(a).trim().equals(normalizarTexto(b).trim());
    }
}
