<%@page import="com.com.tienda.modelo.entidades.Producto"%>
<%@page import="com.com.tienda.modelo.entidades.Usuario"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Productos</title>
<style>
:root {
    --bg: #f0f2f5; --card: #ffffff; --primary: #4a6cf7; --primary-dark: #3854c8;
    --danger: #ff4d4f; --danger-dark: #cc3d3f; --text: #2c2c2c; --subtext: #666; --shadow: rgba(0,0,0,0.12);
}
body { margin: 0; padding: 0; background: var(--bg); font-family: "Segoe UI", Arial, sans-serif; }
h1 { text-align: center; margin: 30px 0 10px; font-size: 42px; letter-spacing: -1px; color: var(--primary-dark); font-weight: 900; }
.header-links { display: flex; justify-content: center; gap: 20px; margin-bottom: 25px; }
.btn { padding: 12px 20px; border-radius: 8px; background: var(--primary); color: white; text-decoration: none; font-weight: 600; transition: 0.25s; box-shadow: 0 3px 8px var(--shadow); }
.btn:hover { background: var(--primary-dark); transform: translateY(-2px); }
.btn-danger { background: var(--danger); }
.btn-danger:hover { background: var(--danger-dark); }
.grid { max-width: 1200px; margin: 0 auto; padding: 10px; display: grid; grid-template-columns: repeat(auto-fill, minmax(260px, 1fr)); gap: 25px; }
.product-card { background: var(--card); border-radius: 15px; padding: 15px; box-shadow: 0 6px 15px var(--shadow); display: flex; flex-direction: column; transition: 0.3s; }
.product-card:hover { transform: translateY(-5px); box-shadow: 0 10px 20px var(--shadow); }
.product-img { width: 100%; height: 170px; border-radius: 12px; background-size: cover; background-position: center; margin-bottom: 10px; }
.product-name { font-size: 20px; font-weight: 700; color: var(--text); margin-bottom: 5px; }
.product-price { font-size: 18px; font-weight: bold; color: var(--primary-dark); margin-bottom: 10px; }
.product-actions { margin-top: auto; display: flex; gap: 8px; }
.empty-msg { text-align: center; font-size: 20px; color: var(--subtext); margin-top: 50px; }
</style>
</head>
<body>

<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null) { response.sendRedirect("login.jsp"); return; }
    List<Producto> lista = (List<Producto>) request.getAttribute("productos");
%>

<h1>Catálogo de Productos</h1>

<div class="header-links">
    <a href="NuevoProducto.jsp" class="btn">➕ Agregar Producto</a>
    <a href="logout" class="btn btn-danger">Cerrar Sesión</a>
</div>

<div class="grid">
<%
if (lista != null && !lista.isEmpty()) {
    for (Producto p : lista) {
%>
    <div class="product-card">
        <div class="product-img" style="background-image: url('ImagenProducto?id=<%= p.getId() %>');"></div>
        <div class="product-name"><%= p.getNombre() %></div>
        <div class="product-price">$<%= p.getPrecio() %></div>
        <div class="product-actions">
            <a href="editarProducto?id=<%= p.getId() %>" class="btn" style="flex:1;">Editar</a>
            <a href="EliminarProducto?id=<%= p.getId() %>" onclick="return confirm('¿Eliminar este producto?');" class="btn btn-danger" style="flex:1;">Eliminar</a>
        </div>
    </div>
<%
    }
} else {
%>
<div class="empty-msg">No hay productos disponibles.</div>
<%
}
%>
</div>

</body>
</html>
