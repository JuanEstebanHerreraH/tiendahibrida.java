package com.com.tienda.modelo.dao;

import com.com.tienda.modelo.entidades.Usuario;
import java.sql.*;

public class UsuarioDAO {

    // LOGIN
    public Usuario login(String email, String password) {

        String sql = "SELECT id, nombre, email, rol, moneda_preferida "
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

        String sql = "SELECT * FROM usuarios WHERE email = ?";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setMonedaPreferida(rs.getString("moneda_preferida"));
                u.setRol(rs.getString("rol"));
                return u;
            }

        }

        return null;
    }


    // REGISTRAR NUEVO USUARIO (ROL: CLIENTE)
    public boolean registrar(Usuario u) {

        String sql = "INSERT INTO usuarios (nombre, email, password, moneda_preferida, rol) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getMonedaPreferida());
            ps.setString(5, u.getRol());  // cliente

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error registrando usuario: " + e.getMessage());
            return false;
        }
    }

}
