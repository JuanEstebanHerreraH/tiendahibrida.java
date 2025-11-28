package com.tiendawweb.controladores;

import com.com.tienda.modelo.dao.ProductoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import com.tienda.modelo.entidades.Producto;

@WebServlet("/listarProductos")
public class ListarProductos extends HttpServlet {

    private final ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Producto> lista = dao.listar();
        request.setAttribute("lista", lista);

        request.getRequestDispatcher("productos.jsp").forward(request, response);
    }
}
