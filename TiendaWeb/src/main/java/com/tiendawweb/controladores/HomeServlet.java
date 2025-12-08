package com.tiendawweb.controladores;

import com.com.tienda.modelo.dao.ProductoDAO;
import com.com.tienda.modelo.entidades.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/tienda")
public class HomeServlet extends HttpServlet {

    private final ProductoDAO productoDAO = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Obtener parámetro de categoría
            String cat = request.getParameter("cat");
            List<Producto> productos;

            if (cat != null && !cat.isEmpty()) {
                productos = productoDAO.obtenerPorCategoria(cat);
            } else {
                productos = productoDAO.listar();
            }

            // Pasar productos y categorías al JSP
            request.setAttribute("productos", productos);
            request.setAttribute("categorias", productoDAO.obtenerCategorias());

        } catch (Exception e) {
            throw new ServletException("Error al cargar datos de la tienda", e);
        }

        request.getRequestDispatcher("Tienda.jsp").forward(request, response);
    }
}

