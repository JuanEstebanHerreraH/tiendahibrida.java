package com.tiendawweb.controladores;



import com.com.tienda.modelo.dao.UsuarioDAO;
import com.com.tienda.modelo.entidades.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/registroUsuario")
public class RegistroUsuario extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Recibir datos del formulario
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Validar si el correo ya existe
            if (usuarioDAO.buscarPorEmail(email) != null) {
                request.setAttribute("error", "El correo ya está registrado.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                return;
            }

            // Crear usuario cliente
            Usuario nuevo = new Usuario();
            nuevo.setNombre(nombre);
            nuevo.setEmail(email);
            nuevo.setPassword(password);
            nuevo.setRol("cliente");
            nuevo.setMonedaPreferida("COP");

            usuarioDAO.registrar(nuevo);

            // Redirigir al login
            response.sendRedirect("login.jsp?registro=ok");

        } catch (Exception e) {
            throw new ServletException("Error en el registro de usuario", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Si alguien entra por GET se redirige al formulario
        response.sendRedirect("registro.jsp");
    }
}
