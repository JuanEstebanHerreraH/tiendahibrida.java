<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.com.tienda.modelo.entidades.Producto" %>

<%
    List<String> categorias = (List<String>) request.getAttribute("categorias");
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");

    if (categorias == null) categorias = new java.util.ArrayList<>();
    if (productos == null) productos = new java.util.ArrayList<>();

    String catActiva = request.getParameter("cat");

    HttpSession ses = request.getSession(false);
    String rol = (ses != null && ses.getAttribute("rol") != null) ? (String) ses.getAttribute("rol") : null;
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Tienda</title>
    <style>
        body { margin: 0; background: #f5f6fa; font-family: 'Segoe UI', sans-serif; }
        header { background: white; padding: 15px 40px; display: flex; justify-content: space-between; align-items: center; box-shadow: 0 2px 10px rgba(0,0,0,0.05); position: sticky; top: 0; z-index: 10; }
        header h2 { margin: 0; color: #0077ff; }
        header a { text-decoration: none; color: #333; margin-left: 20px; font-weight: 600; }
        .btn-logout { padding: 8px 15px; border-radius: 6px; background: #ff3b3b; color: white; }
        .layout { display: grid; grid-template-columns: 250px 1fr; gap: 25px; padding: 25px 40px; }
        .sidebar { background: white; padding: 20px; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.06); height: fit-content; }
        .sidebar h3 { margin-top: 0; margin-bottom: 15px; font-size: 20px; color: #0077ff; }
        .categoria { display: block; padding: 10px; margin-bottom: 5px; border-radius: 8px; text-decoration: none; color: #333; transition: 0.2s; font-size: 15px; }
        .categoria:hover { background: #e7efff; }
        .categoria-activa { background: #0077ff; color: white !important; font-weight: bold; }
        .productos { display: grid; grid-template-columns: repeat(auto-fill, minmax(230px, 1fr)); gap: 25px; }
        .card { background: white; border-radius: 12px; padding: 15px; box-shadow: 0 2px 8px rgba(0,0,0,0.06); transition: 0.2s; }
        .card:hover { transform: translateY(-4px); box-shadow: 0 4px 14px rgba(0,0,0,0.1); }
        .card img { width: 100%; height: 180px; object-fit: cover; border-radius: 10px; }
        .nombre { font-size: 17px; font-weight: bold; margin: 10px 0 5px; }
        .precio { color: #0077ff; font-size: 18px; font-weight: bold; margin-bottom: 5px; }
        .stock { font-size: 14px; color: #555; margin-bottom: 10px; }
        .btn { display: block; text-align: center; padding: 10px; border-radius: 8px; background: #0077ff; color: white; text-decoration: none; font-weight: 600; transition: 0.2s; }
        .btn:hover { background: #005bd1; }
        .btn-disabled { background: #999; pointer-events: none; }
        .warning-login { color: #b30000; font-size: 13px; margin-bottom: 10px; }
    </style>
</head>
<body>

<header>
    <h2>🛒 Tienda Web</h2>
    <div>
        <% if (rol == null) { %>
            <a href="login.jsp">Iniciar sesión</a>
        <% } else { %>
            <a class="btn-logout" href="logout">Cerrar sesión</a>
        <% } %>
    </div>
</header>

<div class="layout">
    <div class="sidebar">
        <h3>Categorías</h3>
        <a href="tienda" class="categoria <%= (catActiva == null ? "categoria-activa" : "") %>">Todas</a>
        <% for (String c : categorias) { %>
            <a href="tienda?cat=<%= c %>" class="categoria <%= (c.equals(catActiva) ? "categoria-activa" : "") %>"><%= c %></a>
        <% } %>
    </div>

    <div class="productos">
        <% if (productos.isEmpty()) { %>
            <p>No hay productos disponibles en esta categoría.</p>
        <% } %>

        <% for (Producto p : productos) { %>
            <div class="card">
                <!-- Ruta de imagen corregida -->
                <img src="ImagenProducto?id=<%= p.getId() %>" alt="Producto">
                <div class="nombre"><%= p.getNombre() %></div>
                <div class="precio">$<%= p.getPrecio() %> USD</div>
                <div class="stock">Stock: <%= p.getStock() %></div>

                <% if (rol == null) { %>
                    <div class="warning-login">Inicia sesión para comprar</div>
                    <a class="btn btn-disabled">Agregar al carrito</a>
                <% } else { %>
                    <% if (p.getStock() <= 0) { %>
                        <a class="btn btn-disabled">Agotado</a>
                    <% } else { %>
                        <a class="btn" href="carrito?accion=agregar&id=<%= p.getId() %>">Agregar al carrito</a>
                    <% } %>
                <% } %>
            </div>
        <% } %>
    </div>
</div>

</body>
</html>
