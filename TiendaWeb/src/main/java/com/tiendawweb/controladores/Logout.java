package com.tiendawweb.controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class Logout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Cerrar sesión si existe
        HttpSession s = request.getSession(false);
        if (s != null) {
            s.invalidate();
        }

        // Redirige siempre al login
        response.sendRedirect(request.getContextPath() + "/login.jsp?logout=1");
    }
}
