<%@page import="java.util.List"%>
<%@page import="com.tienda.modelo.entidades.Producto"%>

<%
    List<Producto> carrito = (List<Producto>) session.getAttribute("carrito");
%>

<h1>Tu carrito</h1>

<% if (carrito == null || carrito.isEmpty()) { %>
    <p>Tu carrito está vacío</p>
<% } else { %>

<ul>
<% for (Producto p : carrito) { %>
    <li><%= p.getNombre() %> - $<%= p.getPrecioUSD() %></li>
<% } %>
</ul>
<a href="logout">Cerrar Sesión</a>

<a href="carrito?accion=vaciar">Vaciar carrito</a>

<% } %>
