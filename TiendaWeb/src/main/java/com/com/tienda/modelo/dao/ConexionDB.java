package com.com.tienda.modelo.dao;

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

    public static Connection getConexion() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return null;
        }
    }
}


