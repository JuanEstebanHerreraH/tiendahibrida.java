package com.mycompany.tiendaadmindesktop.Modelo;

import com.mycompany.tiendaadmindesktop.DAO.UsuarioDAO;

public class CrearAdmins {

    public static void main(String[] args) {

        UsuarioDAO dao = new UsuarioDAO();

        dao.crearAdmin("Admin Principal", "admin@admin.com", "1234");

        System.out.println("Admins creados correctamente");
    }
}
