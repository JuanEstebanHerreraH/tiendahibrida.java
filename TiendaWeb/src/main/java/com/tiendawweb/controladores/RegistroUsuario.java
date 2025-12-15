package com.tiendawweb.controladores;

import com.com.tienda.modelo.dao.UsuarioDAO;
import com.com.tienda.modelo.entidades.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/registroUsuario")
public class RegistroUsuario extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String monedaPreferida = request.getParameter("moneda_preferida"); // 🔥

        try {
            if (usuarioDAO.buscarPorEmail(email) != null) {
                request.setAttribute("error", "El correo ya está registrado.");
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                return;
            }

            Usuario nuevo = new Usuario();
            nuevo.setNombre(nombre);
            nuevo.setEmail(email);
            nuevo.setPassword(password); // (luego lo encriptas)
            nuevo.setRol("cliente");
            nuevo.setMonedaPreferida(monedaPreferida); // 🔥

            usuarioDAO.registrar(nuevo);

            response.sendRedirect("login.jsp?registro=ok");

        } catch (Exception e) {
            throw new ServletException("Error en el registro", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendRedirect("registro.jsp");
    }
}
