package com.tiendawweb.controladores;

import com.com.tienda.modelo.dao.ProductoDAO;
import com.com.tienda.modelo.entidades.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ActualizarProducto")
public class ActualizarProducto extends HttpServlet {

    private final ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recibir datos del formulario
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String categoria = request.getParameter("categoria");
        String imagen = request.getParameter("imagen");

        // Crear objeto
        Producto p = new Producto();
        p.setId(id);
        p.setNombre(nombre);
        p.setPrecio(precio);
        p.setStock(stock);
        p.setDescripcion(categoria);
        p.setImagen(imagen);

        // Actualizar en la BD
        dao.actualizar(p);

        // Redirigir a la lista
        response.sendRedirect("productos");
    }
}
