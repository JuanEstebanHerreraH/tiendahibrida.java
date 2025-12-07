<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tienda.modelo.entidades.Producto" %>

<%
    List<String> categorias = (List<String>) request.getAttribute("categorias");
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");

    HttpSession s = request.getSession(false);
    String rol = null;

    if (s != null && s.getAttribute("rol") != null) {
        rol = (String) s.getAttribute("rol");
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Tienda</title>

    <style>
        body {
            background: #f4f4f4;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        /* CONTENEDOR PRINCIPAL */
        .container {
            display: flex;
            gap: 25px;
            padding: 20px;
        }

        /* CAJA DE FILTROS */
        .sidebar {
            width: 220px;
            background: white;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            height: fit-content;
        }

        .sidebar h3 {
            margin-bottom: 15px;
        }

        .categoria-link {
            display: block;
            margin: 5px 0;
            text-decoration: none;
            color: #333;
            font-size: 15px;
            padding: 5px;
            border-radius: 5px;
        }

        .categoria-link:hover {
            background: #ececec;
        }

        /* LISTA DE PRODUCTOS */
        .productos {
            flex: 1;
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
            gap: 20px;
        }

        /* TARJETA PRODUCTO */
        .card {
            background: white;
            border-radius: 10px;
            padding: 15px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            text-align: center;
        }

        .card img {
            width: 100%;
            height: 170px;
            object-fit: cover;
            border-radius: 8px;
        }

        .card h4 {
            margin: 10px 0 5px;
        }

        .card p {
            margin: 5px 0 15px;
            font-size: 14px;
        }

        /* BOTONES */
        .btn {
            padding: 10px;
            background: #0077ff;
            color: white;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            display: block;
            margin-top: 10px;
        }

        .btn:hover {
            background: #005ace;
        }

        .btn-disabled {
            background: #777;
        }

        .login-warning {
            color: #d10000;
            font-size: 14px;
        }

    </style>
</head>
<body>

<div class="container">

    <!-- SIDEBAR - FILTRO DE CATEGORÍAS -->
    <div class="sidebar">
        <h3>Categorías</h3>

        <a href="tienda" class="categoria-link">Todas</a>

        <% for (String cat : categorias) { %>
            <a href="tienda?cat=<%= cat %>" class="categoria-link"><%= cat %></a>
        <% } %>
    </div>

    <!-- PRODUCTOS -->
    <div class="productos">

        <% if (productos.isEmpty()) { %>
            <p>No hay productos en esta categoría.</p>
        <% } %>

        <% for (Producto p : productos) { %>
 <div class="card">
    <img src="<%= p.getImagenURL() %>" alt="Imagen producto">

    <h4><%= p.getNombre() %></h4>
    <p>Precio: $<%= p.getPrecioUSD() %> USD</p>
    <p>Stock: <%= p.getStock() %></p>

    <% if (rol == null) { %>
        <!-- Usuario NO logueado -->
        <p class="login-warning">Debes iniciar sesión para comprar</p>
        <a class="btn btn-disabled" href="login.jsp">Iniciar Sesión</a>

    <% } else { %>
        <!-- Usuario logueado (cliente o admin) -->
         <a class="btn" href="carrito?accion=agregar&id=<%= p.getId() %>">Agregar al carrito</a>
    <% } %>
</div>
        <% } %>

    </div>

</div>

</body>
</html>
