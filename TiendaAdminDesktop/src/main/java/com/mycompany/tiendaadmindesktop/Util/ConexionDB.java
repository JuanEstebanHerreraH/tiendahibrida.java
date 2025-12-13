package com.mycompany.tiendaadmindesktop.Util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

    private static final String URL =
        "jdbc:sqlserver://localhost:1433;databaseName=tienda;encrypt=false";
    private static final String USER = "sa";
    private static final String PASS = "1234";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
