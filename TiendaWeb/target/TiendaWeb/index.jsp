<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.com.tienda.modelo.entidades.Producto" %>
<%@ page import="com.com.com.tienda.servicios.CurrencyService" %>

<%
    List<String> categorias = (List<String>) request.getAttribute("categorias");
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");

    if (categorias == null) categorias = new java.util.ArrayList<>();
    if (productos == null) productos = new java.util.ArrayList<>();

    String catActiva = request.getParameter("cat");

    HttpSession ses = request.getSession(false);
    String rol = (ses != null) ? (String) ses.getAttribute("rol") : null;

    // 🔥 moneda en sesión
    String moneda = (ses != null && ses.getAttribute("monedaPreferida") != null)
            ? (String) ses.getAttribute("monedaPreferida")
            : "USD";

    // 🔥 ARRAY DE MONEDAS
    String[] monedas = { "USD", "COP", "EUR", "ARS", "MXN","PEN" };
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Tienda</title>

<style>
body { margin: 0; background: #eef1f7; font-family: "Segoe UI", sans-serif; }
header {
    background: white;
    padding: 18px 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    box-shadow: 0 2px 12px rgba(0,0,0,.06);
}
header h1 { margin: 0; color: #1a5eff; }

header .acciones a, header select {
    margin-left: 15px;
    padding: 8px 12px;
    border-radius: 8px;
    font-weight: 600;
    text-decoration: none;
}

.logout { background: #ff4747; color: white; }
.admin { background: #1a5eff; color: white; }

.contenedor {
    display: grid;
    grid-template-columns: 240px 1fr;
    gap: 30px;
    padding: 30px 40px;
}

.sidebar {
    background: white;
    padding: 20px;
    border-radius: 14px;
}

.categoria {
    display: block;
    padding: 10px;
    border-radius: 8px;
    text-decoration: none;
    color: #333;
    margin-bottom: 6px;
}
.categoria-activa { background: #1a5eff; color: white; }

.productos {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(240px,1fr));
    gap: 25px;
}

.card {
    background: white;
    border-radius: 14px;
    box-shadow: 0 3px 12px rgba(0,0,0,.07);
    overflow: hidden;
}

.card img {
    width: 100%;
    height: 180px;
    object-fit: cover;
}

.info { padding: 15px; }
.nombre { font-weight: 700; }
.precio { color: #1a5eff; font-size: 18px; font-weight: bold; }
.stock { font-size: 14px; color: #555; }

.btn {
    display: block;
    margin-top: 10px;
    text-align: center;
    background: #1a5eff;
    color: white;
    padding: 10px;
    border-radius: 8px;
    text-decoration: none;
}
.btn-disabled {
    background: #aaa;
    pointer-events: none;
}
</style>
</head>

<body>

<header>
    <h1>🛒 Tienda Web</h1>

    <div class="acciones">
        <% if (rol != null) { %>

            <!-- 🔥 SELECTOR MONEDA -->
<form action="cambiarMoneda" method="get" style="display:inline;">
    <select name="moneda" onchange="this.form.submit()">
        <% for (String m : monedas) { %>
            <option value="<%= m %>" <%= m.equals(moneda) ? "selected" : "" %>>
                <%= m %>
            </option>
        <% } %>
    </select>
</form>

            <% if ("admin".equals(rol)) { %>
                <a href="productos" class="admin">Panel Admin</a>
            <% } %>

            <a href="logout" class="logout">Cerrar sesión</a>

        <% } else { %>
            <a href="login.jsp">Login</a>
            <a href="registro.jsp">Registro</a>
        <% } %>
    </div>
</header>

<div class="contenedor">

    <!-- SIDEBAR -->
    <div class="sidebar">
        <h3>Categorías</h3>
        <a href="tienda" class="categoria <%= catActiva==null?"categoria-activa":"" %>">Todas</a>
        <% for(String c: categorias){ %>
            <a href="tienda?cat=<%=c%>" class="categoria <%=c.equals(catActiva)?"categoria-activa":""%>">
                <%=c%>
            </a>
        <% } %>
    </div>

    <!-- PRODUCTOS -->
    <div class="productos">
        <% for(Producto p: productos){ 
            // 🔥 convertir precio con CurrencyService
            double precioConvertido = CurrencyService.convertir(p.getPrecio(), moneda);
        %>
        <div class="card">
            <img src="ImagenProducto?id=<%=p.getId()%>">
            <div class="info">
                <div class="nombre"><%=p.getNombre()%></div>
                <div class="precio">
                    <%= String.format("%.2f", precioConvertido) %> <%= moneda %>
                </div>
                <div class="stock">Stock: <%=p.getStock()%></div>

                <% if (rol == null || p.getStock() <= 0) { %>
                    <a class="btn btn-disabled">No disponible</a>
                <% } else { %>
                    <a class="btn" href="carrito?accion=agregar&id=<%=p.getId()%>">Agregar</a>
                <% } %>
            </div>
        </div>
        <% } %>
    </div>

</div>

</body>
</html>
