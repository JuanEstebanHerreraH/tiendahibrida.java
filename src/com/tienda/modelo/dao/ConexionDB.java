package com.tienda.modelo.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

    private static final String URL =
        "jdbc:sqlserver://localhost:1433;" +
        "databaseName=tienda_db;" +
        "encrypt=false;" +
        "trustServerCertificate=true;" +
        "integratedSecurity=true;";

    public static Connection getConexion() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("si se conecto mierda");
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.out.println("Error al conectar: " + e.getMessage());
            System.out.println("me voy a matar");
            return null;
        }
    }
}

