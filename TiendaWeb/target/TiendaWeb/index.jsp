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
body { margin: 0; background: #eef1f7; font-family: "Segoe UI", sans-serif; }
header { background: #ffffff; padding: 18px 40px; display: flex; justify-content: space-between; align-items: center; box-shadow: 0 2px 12px rgba(0,0,0,0.06); position: sticky; top: 0; z-index: 100; }
header h1 { margin: 0; font-size: 26px; font-weight: 700; color: #1a5eff; }
header a { text-decoration: none; font-weight: 600; margin-left: 20px; padding: 8px 15px; border-radius: 8px; transition: .2s; }
.login { color: #1a1a1a; }
.logout { background: #ff4747; color: white; }
.logout:hover { background: #d63232; }
.contenedor { display: grid; grid-template-columns: 240px 1fr; gap: 30px; padding: 30px 40px; }
.sidebar { background: #ffffff; padding: 20px; border-radius: 14px; box-shadow: 0 3px 10px rgba(0,0,0,0.06); }
.sidebar h3 { margin-top: 0; font-size: 20px; color: #1a5eff; }
.categoria { display: block; padding: 10px 12px; margin: 6px 0; background: #f4f6fc; border-radius: 8px; text-decoration: none; color: #1a1a1a; font-size: 15px; transition: .25s; }
.categoria:hover { background: #e3eaff; }
.categoria-activa { background: #1a5eff; color: white !important; font-weight: bold; }
.productos { display: grid; grid-template-columns: repeat(auto-fill, minmax(240px, 1fr)); gap: 25px; }
.card { background: white; border-radius: 14px; overflow: hidden; box-shadow: 0 3px 12px rgba(0,0,0,0.07); transition: .2s; }
.card:hover { transform: translateY(-4px); box-shadow: 0 5px 18px rgba(0,0,0,0.12); }
.card img { width: 100%; height: 185px; object-fit: cover; }
.info { padding: 15px; }
.nombre { font-size: 17px; font-weight: 700; margin: 6px 0 4px; }
.precio { color: #1a5eff; font-size: 18px; margin: 3px 0; font-weight: bold; }
.stock { font-size: 14px; color: #555; margin-bottom: 10px; }
.btn { display: block; text-align: center; background: #1a5eff; padding: 10px; margin-top: 10px; border-radius: 8px; color: white; text-decoration: none; font-weight: 600; transition: .2s; }
.btn:hover { background: #0f42c8; }
.btn-disabled { background: #9a9a9a !important; pointer-events: none; }
.login-warning { color: #b30000; font-size: 13px; margin-bottom: 5px; }
</style>
</head>
<body>

<header>
    <h1>🛒 Tienda Web</h1>
    <div>
        <% if (rol == null) { %>
            <a class="login" href="login.jsp">Iniciar sesión</a>
            <a class="login" href="registro.jsp">Registrarse</a>
        <% } else { %>
            <a class="logout" href="logout">Cerrar sesión</a>
        <% } %>
    </div>
</header>

<div class="contenedor">

    <!-- SIDEBAR -->
    <div class="sidebar">
        <h3>Categorías</h3>
        <a href="tienda" class="categoria <%= (catActiva == null ? "categoria-activa" : "") %>">Todas</a>
        <% for (String c : categorias) { %>
            <a href="tienda?cat=<%= c %>" class="categoria <%= (c.equals(catActiva) ? "categoria-activa" : "") %>"><%= c %></a>
        <% } %>
    </div>

    <!-- PRODUCTOS -->
    <div class="productos">
        <% if (productos.isEmpty()) { %>
            <p>No hay productos en esta categoría.</p>
        <% } %>

        <% for (Producto p : productos) { %>
            <div class="card">
               <img src="<%= request.getContextPath() %>/ImagenProducto?id=<%= p.getId() %>" alt="Producto">
                <div class="info">
                    <div class="nombre"><%= p.getNombre() %></div>
                    <div class="precio">$<%= p.getPrecio() %> USD</div>
                    <div class="stock">Stock: <%= p.getStock() %></div>

                    <% if (rol == null) { %>
                        <div class="login-warning">Inicia sesión para comprar</div>
                        <a class="btn btn-disabled">Agregar al carrito</a>
                    <% } else { %>
                        <% if (p.getStock() <= 0) { %>
                            <a class="btn btn-disabled">Agotado</a>
                        <% } else { %>
                            <a class="btn" href="carrito?accion=agregar&id=<%= p.getId() %>">Agregar al carrito</a>
                        <% } %>
                    <% } %>
                </div>
            </div>
        <% } %>
    </div>

</div>

</body>
</html>
