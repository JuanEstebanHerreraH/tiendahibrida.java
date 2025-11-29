package com.tiendawweb.controladores;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.com.tienda.modelo.dao.UsuarioDAO;
import com.com.tienda.modelo.entidades.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {

    private final UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Usuario u = dao.login(email, password);

        if (u != null) {
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", u);

            response.sendRedirect("listarProductos");
        } else {
            request.setAttribute("error", "Correo o contraseña incorrectos");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
