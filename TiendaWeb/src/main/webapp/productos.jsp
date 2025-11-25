<%@page import="java.util.List"%>
<%@page import="com.tienda.modelo.entidades.Producto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Productos</title>
</head>
<body>
<h1>Lista de Productos</h1>

<ul>
<% 
    List<Producto> lista =
        (List<Producto>) request.getAttribute("listaProductos");

    if (lista != null) {
        for (Producto p : lista) {
%>
        <li><%= p.getNombre() %> - $<%= p.getPrecioUSD() %></li>
<%
        }
    }
%>
</ul>

</body>
</html>
