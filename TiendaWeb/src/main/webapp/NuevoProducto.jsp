<%@page import="com.com.tienda.modelo.entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Nuevo Producto</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 500px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }

        h1 {
            text-align: center;
            color: #0077ff;
            margin-bottom: 30px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }

        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
            transition: border-color 0.3s;
        }

        input[type="text"]:focus,
        input[type="number"]:focus {
            border-color: #0077ff;
            outline: none;
        }

        button {
            width: 100%;
            padding: 12px;
            background-color: #0077ff;
            color: white;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #005ace;
        }

        .logout-link {
            display: block;
            text-align: center;
            margin-top: 20px;
        }

        .logout-link a {
            text-decoration: none;
            color: #d10000;
            font-weight: bold;
        }
    </style>
</head>
<body>

<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<div class="container">
    <h1>Registrar Nuevo Producto</h1>

    <form action="GuardarProducto" method="post">
        <label>Nombre:</label>
        <input type="text" name="nombre" required>

        <label>Precio USD:</label>
        <input type="number" name="precio" step="0.01" required>

        <label>Stock:</label>
        <input type="number" name="stock" required>

        <label>Categoría:</label>
        <input type="text" name="categoria" required>

        <label>Imagen URL:</label>
        <input type="text" name="imagen" required>

        <button type="submit">Guardar</button>
    </form>

    <div class="logout-link">
        <a href="logout">Cerrar Sesión</a>
    </div>
</div>

</body>
</html>
