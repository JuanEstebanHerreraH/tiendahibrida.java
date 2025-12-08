package com.tiendawweb.controladores;

import com.com.tienda.modelo.dao.ProductoDAO;
import com.com.tienda.modelo.entidades.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/producto")
public class ProductoDetalleServlet extends HttpServlet {

    private final ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/productos");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            // 🔥 CORREGIDO → usar tu método existente
            Producto p = dao.buscar(id);

            if (p == null) {
                request.setAttribute("error", "Producto no encontrado");
            } else {
                request.setAttribute("producto", p);
            }

            request.getRequestDispatcher("/WEB-INF/productoDetalle.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/productos");
        }
    }
}
