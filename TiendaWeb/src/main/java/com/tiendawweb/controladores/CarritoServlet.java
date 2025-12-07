package com.tiendawweb.controladores;
import com.com.tienda.modelo.dao.ProductoDAO;
import com.tienda.modelo.entidades.Producto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/carrito")
public class CarritoServlet extends HttpServlet {

    private final ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");
        HttpSession session = req.getSession();

        List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        if ("agregar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Producto p = dao.buscar(id);
            carrito.add(p);
            session.setAttribute("carrito", carrito);
            resp.sendRedirect("VerCarrito.jsp");
            return;
        }

        if ("ver".equals(accion)) {
            req.getRequestDispatcher("VerCarrito.jsp").forward(req, resp);
            return;
        }

        if ("vaciar".equals(accion)) {
            session.removeAttribute("carrito");
            resp.sendRedirect("carrito?accion=ver");
            return;
        }
    }
}
