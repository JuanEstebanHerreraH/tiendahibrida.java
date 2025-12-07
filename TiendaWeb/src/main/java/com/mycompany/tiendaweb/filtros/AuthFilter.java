package com.mycompany.tiendaweb.filtros;

import com.com.tienda.modelo.entidades.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/login",
            "/login.jsp",
            "/",
            "/index.jsp",
            "/css/", "/js/", "/images/",
            "/registrarse", "/registrarse.jsp"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI().substring(req.getContextPath().length());

        boolean esPublica = PUBLIC_PATHS.stream().anyMatch(uri::startsWith)
                || uri.matches(".*(\\.css|\\.js|\\.png|\\.jpg)$");

        if (esPublica) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        Usuario u = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        // 🔥 BLOQUEO DE CRUD A CLIENTES
        boolean esAdminRequest =
                uri.startsWith("/productos") ||
                uri.startsWith("/nuevoProducto") ||
                uri.startsWith("/guardarProducto") ||
                uri.startsWith("/editarProducto") ||
                uri.startsWith("/actualizarProducto") ||
                uri.startsWith("/eliminarProducto");

        if (esAdminRequest && !"admin".equals(u.getRol())) {
            resp.sendRedirect(req.getContextPath() + "/tienda");
            return;
        }

        chain.doFilter(request, response);
    }
}
