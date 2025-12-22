package com.mycompany.tiendaadmindesktop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    // Leemos las variables que configuraste en el panel de AWS
    private static final String HOST = System.getenv("DB_HOST");
    private static final String DATABASE = System.getenv("DB_NAME");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASS = System.getenv("DB_PASS");
    private static final String PORT = System.getenv("DB_PORT");

    private static final String URL =
        "jdbc:sqlserver://" + HOST + ":" + PORT + ";"
      + "databaseName=" + DATABASE + ";"
      + "encrypt=false;"
      + "trustServerCertificate=true;"
      + "user=" + USER + ";"
      + "password=" + PASS + ";";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.err.println("Error conexión SQL: " + e.getMessage());
            return null;
        }
    }
}