package com.com.tienda.modelo.dao;

import com.com.tienda.modelo.entidades.Usuario;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;
public class UsuarioDAO {

    // LOGIN
public Usuario login(String email, String passwordPlano) {

    String sql = """
        SELECT id, nombre, email, password, rol, moneda_preferida
        FROM usuarios
        WHERE email = ?
    """;

    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            String hashBD = rs.getString("password");

            // 🔐 COMPARACIÓN CORRECTA
            if (BCrypt.checkpw(passwordPlano, hashBD)) {

                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setRol(rs.getString("rol"));
                u.setMonedaPreferida(rs.getString("moneda_preferida"));

                return u;
            }
        }

    } catch (Exception e) {
        System.out.println("Error en login: " + e.getMessage());
    }

    return null;
}

// BUSCAR USUARIO POR EMAIL
public Usuario buscarPorEmail(String email) throws Exception {

    String sql = "SELECT id, nombre, email, password, rol, moneda_preferida FROM usuarios WHERE email = ?";

    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Usuario u = new Usuario();
            u.setId(rs.getInt("id"));
            u.setNombre(rs.getString("nombre"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password")); // HASH
            u.setRol(rs.getString("rol"));
            u.setMonedaPreferida(rs.getString("moneda_preferida"));
            return u;
        }
    }

    return null;
}


    // REGISTRAR NUEVO USUARIO (ROL: CLIENTE)
public boolean registrar(Usuario u) {

    String sql = """
        INSERT INTO usuarios (nombre, email, password, moneda_preferida, rol)
        VALUES (?, ?, ?, ?, ?)
    """;

    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        String hash = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());

        ps.setString(1, u.getNombre());
        ps.setString(2, u.getEmail());
        ps.setString(3, hash); // 🔐 HASH
        ps.setString(4, u.getMonedaPreferida());
        ps.setString(5, u.getRol());

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
public void actualizarMonedaPreferida(String email, String nuevaMoneda) {
    String query = "UPDATE usuarios SET moneda_preferida = ? WHERE email = ?"; // columna correcta
    try (Connection conn = ConexionDB.getConexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, nuevaMoneda);
        stmt.setString(2, email);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
