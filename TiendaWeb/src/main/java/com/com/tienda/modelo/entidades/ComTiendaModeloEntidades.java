
package com.com.tienda.modelo.entidades;


import com.com.tienda.modelo.dao.ConexionDB;
import com.com.tienda.modelo.dao.ProductoDAO;
import java.sql.Connection;


public class ComTiendaModeloEntidades {


    public static void main(String[] args) {
       ProductoDAO dao = new ProductoDAO();
    System.out.println(dao.listar());
    
  Connection con = ConexionDB.getConexion();

        if (con != null) {
            System.out.println("✅ Conexión exitosa a SQL Server mediante ConexionDB");
        } else {
            System.out.println("❌ No se pudo conectar (con es null)");
        }
    
    }
    
}
