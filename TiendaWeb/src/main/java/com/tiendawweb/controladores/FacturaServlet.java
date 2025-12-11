package com.tiendawweb.controladores;

import com.com.tienda.modelo.dao.CarritoDAO;
import com.com.tienda.modelo.entidades.Venta;
import com.com.tienda.modelo.entidades.Producto;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/factura")
public class FacturaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idVenta = Integer.parseInt(request.getParameter("id"));

            CarritoDAO dao = new CarritoDAO();

            // Cargar la venta
            Venta venta = dao.obtenerVenta(idVenta);

            // Cargar los detalles
            List<Producto> detalles = dao.obtenerDetallesVenta(idVenta);

            request.setAttribute("venta", venta);
            request.setAttribute("detalles", detalles);

            request.getRequestDispatcher("Factura.jsp").forward(request, response);

        } catch (Exception e) {
            throw new ServletException("Error al cargar la factura", e);
        }
    }
}
