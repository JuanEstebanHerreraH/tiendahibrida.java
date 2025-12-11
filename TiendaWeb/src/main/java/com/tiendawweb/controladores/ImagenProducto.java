package com.tiendawweb.controladores;


import com.com.tienda.modelo.dao.ProductoDAO;
import com.com.tienda.modelo.entidades.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ImagenProducto")
public class ImagenProducto extends HttpServlet {
    private final ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Producto p = dao.buscar(id);

        if (p != null && p.getImagenBlob() != null) {
            response.setContentType("image/png"); // o "image/jpeg" según tu imagen
            response.getOutputStream().write(p.getImagenBlob());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

