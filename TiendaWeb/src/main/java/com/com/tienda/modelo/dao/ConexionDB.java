package com.com.tienda.modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL;
    private static final String USER;
    private static final String PASS;

    static {
String host = System.getenv("DB_HOST");
String port = System.getenv("DB_PORT");
String db   = System.getenv("DB_NAME");
URL  = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + db + ";encrypt=false;trustServerCertificate=true;";
USER = System.getenv("DB_USER");
PASS = System.getenv("DB_PASS");

    }
public static Connection getConexion() throws SQLException {
    try {
        System.out.println("INTENTANDO CONECTAR A SQL SERVER...");
        System.out.println("HOST=" + System.getenv("DB_HOST"));
        System.out.println("USER=" + System.getenv("DB_USER"));

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        Connection c = DriverManager.getConnection(URL, USER, PASS);

        System.out.println("CONEXIÓN OK A SQL SERVER");
        return c;

    } catch (Exception e) {
        e.printStackTrace();
        throw new SQLException(e);
    }
}
public class TestConexion {
    public static void main(String[] args) {
        try {
            System.out.println("Listando productos...");
            ProductoDAO dao = new ProductoDAO();
            System.out.println(dao.listar());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

}
