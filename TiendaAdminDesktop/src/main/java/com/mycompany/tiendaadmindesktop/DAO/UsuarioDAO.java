package com.mycompany.tiendaadmindesktop.DAO;

import com.mycompany.tiendaadmindesktop.util.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {

public void crearAdmin(String nombre, String email, String passwordPlano) {

    String sql = """
        INSERT INTO usuarios (nombre, email, password, moneda_preferida, rol)
        VALUES (?, ?, ?, ?, ?)
    """;

    try (Connection con = ConexionDB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        String hash = BCrypt.hashpw(passwordPlano, BCrypt.gensalt());

        ps.setString(1, nombre);
        ps.setString(2, email);
        ps.setString(3, hash);
        ps.setString(4, "XX");   // o la moneda que uses
        ps.setString(5, "admin");

        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public boolean loginAdmin(String email, String passwordPlano) {

    String sql = """
        SELECT password
        FROM usuarios
        WHERE email = ? AND rol = 'admin'
    """;

    try (Connection con = ConexionDB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String hash = rs.getString("password");
            return BCrypt.checkpw(passwordPlano, hash);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

}
