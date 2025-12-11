package com.tiendawweb.controladores;

import com.com.tienda.modelo.dao.ProductoDAO;
import com.com.tienda.modelo.entidades.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/GuardarProducto")
@MultipartConfig
public class GuardarProducto extends HttpServlet {

    private final ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String nombre = request.getParameter("nombre");
            String precioStr = request.getParameter("precio");
            String stockStr = request.getParameter("stock");
            String categoria = request.getParameter("categoria");

            if(precioStr == null || precioStr.trim().isEmpty()) precioStr = "0";
            if(stockStr == null || stockStr.trim().isEmpty()) stockStr = "0";

            double precio = Double.parseDouble(precioStr);
            int stock = Integer.parseInt(stockStr);

            Producto p = new Producto();
            p.setNombre(nombre);
            p.setPrecio(precio);
            p.setStock(stock);
            p.setDescripcion(categoria);

            // --- GUARDAR IMAGEN COMO BLOB ---
            Part filePart = request.getPart("imagen");
            if(filePart != null && filePart.getSize() > 0){
                InputStream is = filePart.getInputStream();
                byte[] data = is.readAllBytes();
                p.setImagenBlob(data);
            }

            dao.agregar(p);

            response.sendRedirect("productos");

        } catch(Exception e){
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
