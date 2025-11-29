package com.com.tienda.modelo.dao;

import com.com.tienda.modelo.entidades.Usuario;
import java.sql.*;

public class UsuarioDAO {

    public Usuario login(String email, String password) {

        String sql = "SELECT id, nombre, email, moneda_preferida "
                   + "FROM usuarios WHERE email = ? AND password = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Usuario u = new Usuario();
                    u.setId(rs.getInt("id"));
                    u.setNombre(rs.getString("nombre"));
                    u.setEmail(rs.getString("email"));
                    u.setMonedaPreferida(rs.getString("moneda_preferida"));

                    return u;
                }
            }

        } catch (Exception e) {
            System.out.println("Error en login: " + e.getMessage());
        }

        return null;
    }
}

