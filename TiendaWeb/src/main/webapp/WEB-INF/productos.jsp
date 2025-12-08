<%@page import="com.com.tienda.modelo.entidades.Producto"%>
<%@page import="com.com.tienda.modelo.entidades.Usuario"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Productos</title>
    <meta charset="UTF-8">
    <style>
        body {
            background: #f4f4f4;
            font-family: Arial, sans-serif;
            padding: 20px;
        }

        h1 {
            text-align: center;
            color: #0077ff;
        }

        .btn {
            display: inline-block;
            padding: 8px 15px;
            margin: 5px;
            background-color: #0077ff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: background 0.3s;
        }

        .btn:hover {
            background-color: #005ace;
        }

        .btn-danger {
            background-color: #d10000;
        }

        .btn-danger:hover {
            background-color: #a00000;
        }

        .links {
            text-align: center;
            margin-bottom: 20px;
        }

        ul {
            list-style: none;
            padding: 0;
            width: 600px;
            margin: 0 auto;
        }

        li {
            background: white;
            margin-bottom: 10px;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 0 8px rgba(0,0,0,0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        li span {
            font-weight: bold;
        }

        li a {
            margin-left: 10px;
        }

    </style>
</head>
<body>

<h1>Lista de Productos</h1>

<div class="links">
    <a href="NuevoProducto.jsp" class="btn">Agregar Producto</a>
    <a href="logout" class="btn btn-danger">Cerrar Sesión</a>
</div>

<ul>
<%
    List<Producto> lista = (List<Producto>) request.getAttribute("productos");

    if (lista != null && !lista.isEmpty()) {
        for (Producto p : lista) {
%>
    <li>
        <span><%= p.getNombre() %> - $<%= p.getPrecio() %></span>
        <div>
            <a href="editarProducto?id=<%= p.getId() %>" class="btn">Editar</a>
            <a href="EliminarProducto?id=<%= p.getId() %>" class="btn btn-danger"
               onclick="return confirm('¿Eliminar este producto?');">Eliminar</a>
        </div>
    </li>
<%
        }
    } else {
%>
    <li>No hay productos para mostrar.</li>
<%
    }
%>
</ul>

<%
    Usuario u = (Usuario) session.getAttribute("usuario");

    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

</body>
</html>
