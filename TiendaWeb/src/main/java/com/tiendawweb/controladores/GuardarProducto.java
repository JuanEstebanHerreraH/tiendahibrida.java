package com.tiendawweb.controladores;

import com.com.tienda.modelo.dao.ProductoDAO;
import com.tienda.modelo.entidades.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/GuardarProducto")
public class GuardarProducto extends HttpServlet {

    private final ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Recibir los datos del formulario
        String nombre = request.getParameter("nombre");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        // 2. Crear el objeto Producto
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setPrecioUSD(precio);
        p.setStock(stock);

        // 3. Guardarlo con DAO
        dao.agregar(p);

        // 4. Redirigir a la lista
        response.sendRedirect("productos");
    }
}
