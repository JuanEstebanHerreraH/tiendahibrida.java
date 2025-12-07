package com.tiendawweb.controladores;

import com.com.tienda.modelo.dao.ProductoDAO;
import com.tienda.modelo.entidades.Producto;
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

        String categoria = request.getParameter("cat");

        try {
            List<String> categorias = productoDAO.obtenerCategorias();

            List<Producto> productos =
                    (categoria == null || categoria.isEmpty())
                            ? productoDAO.listar()
                            : productoDAO.listarPorCategoria(categoria);

            request.setAttribute("categorias", categorias);
            request.setAttribute("productos", productos);

        } catch (Exception e) {
            throw new ServletException("Error al cargar datos de la tienda", e);
        }

        request.getRequestDispatcher("Tienda.jsp").forward(request, response);
    }
}
