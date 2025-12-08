package com.tiendawweb.controladores;

import com.com.tienda.modelo.dao.CarritoDAO;
import com.com.tienda.modelo.entidades.Producto;
import com.com.tienda.modelo.entidades.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/carrito")
public class CarritoServlet extends HttpServlet {

    private final CarritoDAO carritoDAO = new CarritoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            resp.sendRedirect("login");
            return;
        }

        int idUsuario = usuario.getId();
        String accion = req.getParameter("accion");

        try {
            switch (accion) {
                case "agregar" -> {
                    int idProducto = Integer.parseInt(req.getParameter("id"));
                    int stockBase = carritoDAO.obtenerStockProducto(idProducto);
                    int cantidadActual = carritoDAO.obtenerCantidadEnCarrito(idUsuario, idProducto);

                    if (cantidadActual < stockBase) {
                        carritoDAO.agregarProducto(idUsuario, idProducto);
                    }
                    resp.sendRedirect("carrito?accion=ver");
                }

                case "ver" -> {
                    List<Producto> carrito = carritoDAO.obtenerCarrito(idUsuario);
                    req.setAttribute("carrito", carrito);
                    req.getRequestDispatcher("VerCarrito.jsp").forward(req, resp);
                }

                case "vaciar" -> {
                    carritoDAO.vaciarCarrito(idUsuario);
                    resp.sendRedirect("carrito?accion=ver");
                }
case "eliminar" -> {
    int idProducto = Integer.parseInt(req.getParameter("id"));
    carritoDAO.eliminarProducto(idUsuario, idProducto);
    resp.sendRedirect("carrito?accion=ver");
}

                default -> resp.sendRedirect("tienda");
            }
        } catch (Exception e) {
            throw new ServletException("Error en carrito", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            resp.sendRedirect("login");
            return;
        }

        int idUsuario = usuario.getId();

        try {
            List<Producto> carrito = carritoDAO.obtenerCarrito(idUsuario);

            for (Producto p : carrito) {
                String param = req.getParameter("cantidad_" + p.getId());
                if (param != null) {
                    int cantidad = Integer.parseInt(param);

                    // Limitar cantidad al stock real
                    int stockBase = carritoDAO.obtenerStockProducto(p.getId());
                    if (cantidad < 1) cantidad = 1;
                    if (cantidad > stockBase) cantidad = stockBase;

                    carritoDAO.actualizarCantidad(idUsuario, p.getId(), cantidad);
                }
            }

            resp.sendRedirect("carrito?accion=ver");

        } catch (Exception e) {
            throw new ServletException("Error al actualizar el carrito", e);
        }
    }
}
