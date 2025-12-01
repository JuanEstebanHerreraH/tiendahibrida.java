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

<!-- BOTÓN PARA AGREGAR -->
<a href="NuevoProducto.jsp">
    <button>Agregar Producto</button>
</a>
<br><br>

<ul>
<%
    List<Producto> lista = (List<Producto>) request.getAttribute("productos");

    if (lista != null) {
        for (Producto p : lista) {
%>

    <li>
        <%= p.getNombre() %> - $<%= p.getPrecioUSD() %>

        
        <a href="editarProducto?id=<%= p.getId() %>">Editar</a>

       
        <a href="EliminarProducto?id=<%= p.getId() %>"
           onclick="return confirm('¿Eliminar este producto?');">
           Eliminar
        </a>
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

</body>
</html>

