<%@page import="com.com.tienda.modelo.entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Nuevo Producto</title>
</head>
<body>

<h1>Registrar Nuevo Producto</h1>

<form action="GuardarProducto" method="post">

    Nombre: <br>
    <input type="text" name="nombre" required><br><br>

    Precio USD: <br>
    <input type="number" name="precio" step="0.01" required><br><br>

    Stock: <br>
    <input type="number" name="stock" required><br><br>

    <button type="submit">Guardar</button>

</form>
<a href="logout">Cerrar Sesión</a>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");

    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

</body>
</html>
