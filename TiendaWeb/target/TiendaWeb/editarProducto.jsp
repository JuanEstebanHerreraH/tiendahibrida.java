<%@page import="com.com.tienda.modelo.entidades.Usuario"%>
<%@page import="com.tienda.modelo.entidades.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Editar Producto</title>
    <meta charset="UTF-8">
</head>
<body>

<%
    Producto p = (Producto) request.getAttribute("producto");

    if (p == null) {
%>
        <h2>Error: no se pudo cargar el producto.</h2>
        <a href="productos">Volver</a>
<%
        return;
    }
%>

<h1>Editar Producto</h1>

<form action="ActualizarProducto" method="post">

    <input type="hidden" name="id" value="<%= p.getId() %>">

    Nombre:  
    <input type="text" name="nombre" value="<%= p.getNombre() %>">  
    <br><br>

    Precio USD:  
    <input type="number" step="0.01" name="precio" value="<%= p.getPrecioUSD() %>">  
    <br><br>

    Stock:  
    <input type="number" name="stock" value="<%= p.getStock() %>">  
    <br><br>

    Categoría:  
    <input type="text" name="categoria" value="<%= p.getCategoria() %>">  
    <br><br>

    Imagen URL:  
    <input type="text" name="imagen" value="<%= p.getImagenURL() %>">  
    <br><br>

    <button type="submit">Guardar Cambios</button>

</form>

<br>
<a href="logout">Cerrar Sesión</a>

<a href="productos">Volver a la lista</a>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");

    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

</body>
</html>

