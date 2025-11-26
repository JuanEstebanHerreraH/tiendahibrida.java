package com.tiendawweb.controladores;

import com.com.tienda.modelo.dao.ProductoDAO;
import com.tienda.modelo.entidades.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editarProducto")
public class EditarProductoServlet extends HttpServlet {

    private final ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recibir ID del producto
        int id = Integer.parseInt(request.getParameter("id"));

        // Buscar en la base de datos
        Producto p = dao.buscar(id);

        // Enviar el producto a la JSP
        request.setAttribute("producto", p);

        // Redirigir a la vista
        request.getRequestDispatcher("editarProducto.jsp")
               .forward(request, response);
    }
}
