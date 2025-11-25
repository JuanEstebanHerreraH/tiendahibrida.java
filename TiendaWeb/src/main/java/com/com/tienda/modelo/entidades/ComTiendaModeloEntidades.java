
package com.com.tienda.modelo.entidades;

import com.com.tienda.modelo.dao.ProductoDAO;


public class ComTiendaModeloEntidades {


    public static void main(String[] args) {
       ProductoDAO dao = new ProductoDAO();
    System.out.println(dao.listar());
    }
    
}
