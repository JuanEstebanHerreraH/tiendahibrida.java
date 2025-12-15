package com.tiendawweb.controladores;

import com.com.tienda.modelo.dao.UsuarioDAO;
import com.com.tienda.modelo.entidades.Usuario;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/cambiarMoneda")
public class MonedaServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String moneda = request.getParameter("moneda");
        if (moneda == null) moneda = "USD";

        HttpSession sesion = request.getSession();
        sesion.setAttribute("monedaPreferida", moneda); // guardar en sesión

        // actualizar la moneda del usuario logueado
        Usuario u = (Usuario) sesion.getAttribute("usuario");
        if (u != null) {
            u.setMonedaPreferida(moneda);
            usuarioDAO.actualizarMonedaPreferida(u.getEmail(), moneda); // actualizar en BD
            sesion.setAttribute("usuario", u); // actualizar sesión
        }

        response.sendRedirect("tienda"); // redirigir a la tienda
    }
}
