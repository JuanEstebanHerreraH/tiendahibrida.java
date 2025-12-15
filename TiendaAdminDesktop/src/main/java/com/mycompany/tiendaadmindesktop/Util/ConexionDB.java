package com.mycompany.tiendaadmindesktop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL =
        "jdbc:sqlserver://localhost:1433;"
      + "databaseName=tienda_db;"
      + "encrypt=false;"
      + "trustServerCertificate=true;"
      + "user=tienda_app;"
      + "password=tienda2024;";

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Error conexión SQL: " + e.getMessage());
            return null;
        }
    }
}
