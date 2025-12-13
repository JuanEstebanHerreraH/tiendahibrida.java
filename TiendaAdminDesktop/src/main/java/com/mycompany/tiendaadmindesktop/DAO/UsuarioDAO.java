package com.mycompany.tiendaadmindesktop.DAO;

import com.mycompany.tiendaadmindesktop.Util.ConexionDB;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {

    public boolean loginAdmin(String usuario, String password) {

        String sql = """
            SELECT 1
            FROM usuarios
            WHERE usuario = ?
              AND password = ?
              AND rol = 'ADMIN'
        """;

        try (Connection con = ConexionDB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
           String hash = BCrypt.hashpw(password, BCrypt.gensalt());
           ps.setString(2, hash);


            ResultSet rs = ps.executeQuery();
            return rs.next(); // true si existe

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
