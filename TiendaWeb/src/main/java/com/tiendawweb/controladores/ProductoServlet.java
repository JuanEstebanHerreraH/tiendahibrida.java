package com.tiendawweb.controladores;

import com.com.tienda.modelo.dao.ProductoDAO;
import com.tienda.modelo.entidades.Producto;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private final ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Producto> lista = dao.listar();
        request.setAttribute("productos", lista);
        request.getRequestDispatcher("/WEB-INF/productos.jsp").forward(request, response);
    }
}

