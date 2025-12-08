<%@page import="com.com.tienda.modelo.entidades.Producto"%>
<%@page import="com.com.tienda.modelo.entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Producto</title>
    <meta charset="UTF-8">
    <style>
        body {
            background: #f4f4f4;
            font-family: Arial, sans-serif;
            padding: 20px;
        }

        h1, h2 {
            text-align: center;
            color: #0077ff;
        }

        form {
            background: white;
            padding: 20px;
            width: 400px;
            margin: 20px auto;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 8px;
            margin: 5px 0 15px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button {
            background-color: #0077ff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
        }

        button:hover {
            background-color: #005ace;
        }

        .links {
            text-align: center;
            margin-top: 15px;
        }

        .links a {
            display: inline-block;
            margin: 5px;
            padding: 8px 12px;
            background-color: #0077ff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
        }

        .links a:hover {
            background-color: #005ace;
        }

        .links a.logout {
            background-color: #d10000;
        }

        .links a.logout:hover {
            background-color: #a00000;
        }
    </style>
</head>
<body>

<%
    Producto p = (Producto) request.getAttribute("producto");

    if (p == null) {
%>
        <h2>Error: no se pudo cargar el producto.</h2>
        <div class="links">
            <a href="productos">Volver</a>
        </div>
<%
        return;
    }

    Usuario u = (Usuario) session.getAttribute("usuario");

    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<h1>Editar Producto</h1>

<form action="ActualizarProducto" method="post">

    <input type="hidden" name="id" value="<%= p.getId() %>">

    Nombre:  
    <input type="text" name="nombre" value="<%= p.getNombre() %>">  

    Precio USD:  
    <input type="number" step="0.01" name="precio" value="<%= p.getPrecio() %>">  

    Stock:  
    <input type="number" name="stock" value="<%= p.getStock() %>">  

    Categoría:  
    <input type="text" name="categoria" value="<%= p.getDescripcion() %>">  

    Imagen URL:  
    <input type="text" name="imagen" value="<%= p.getImagen() %>">  

    <button type="submit">Guardar Cambios</button>

</form>

<div class="links">
    <a href="logout" class="logout">Cerrar Sesión</a>
    <a href="productos">Volver a la lista</a>
</div>

</body>
</html>
