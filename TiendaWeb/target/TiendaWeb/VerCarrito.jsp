<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="com.com.tienda.modelo.entidades.Producto"%>
<%@ page import="com.com.tienda.modelo.entidades.Usuario"%>
<%@ page import="com.com.tienda.modelo.dao.CarritoDAO"%>
<%@ page import="com.com.com.tienda.servicios.CurrencyService"%>
<%@ page import="java.util.List"%>

<%
CarritoDAO carritoDAO = new CarritoDAO();
List<Producto> carrito = (List<Producto>) request.getAttribute("carrito");
if (carrito == null) carrito = new java.util.ArrayList<>();

Usuario u = (Usuario) session.getAttribute("usuario");
String moneda = (u != null && u.getMonedaPreferida() != null)
        ? u.getMonedaPreferida()
        : "USD";

double totalGeneral = 0;
for (Producto p : carrito) {
    double precioConvertido = CurrencyService.convertir(p.getPrecio(), moneda);
    totalGeneral += precioConvertido * p.getStock();
}
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Carrito</title>

<style>
body {
    margin: 0;
    background: #f4f6fb;
    font-family: 'Segoe UI', sans-serif;
}

.container {
    max-width: 1100px;
    margin: 40px auto;
    padding: 20px;
}

.card {
    background: #fff;
    border-radius: 14px;
    padding: 30px;
    box-shadow: 0 10px 30px rgba(0,0,0,.08);
}

h1 {
    text-align: center;
    margin-bottom: 30px;
    color: #243b72;
    font-size: 34px;
}

table {
    width: 100%;
    border-collapse: collapse;
}

thead {
    background: #243b72;
    color: white;
}

th, td {
    padding: 14px;
    text-align: center;
}

tbody tr {
    border-bottom: 1px solid #eee;
}

tbody tr:hover {
    background: #f0f4ff;
}

.producto {
    display: flex;
    align-items: center;
    gap: 12px;
}

.producto img {
    width: 75px;
    height: 75px;
    object-fit: cover;
    border-radius: 10px;
}

input[type="number"] {
    width: 70px;
    padding: 6px;
    border-radius: 6px;
    border: 1px solid #bbb;
    text-align: center;
}

tfoot td {
    background: #e6edff;
    font-size: 20px;
    font-weight: bold;
    border-top: 2px solid #243b72;
}

.actions {
    margin-top: 30px;
    display: flex;
    flex-wrap: wrap;
    gap: 12px;
    justify-content: center;
}

.btn {
    padding: 14px 22px;
    border-radius: 10px;
    font-weight: 600;
    text-decoration: none;
    border: none;
    cursor: pointer;
    font-size: 15px;
    transition: .25s;
}

.btn-blue {
    background: #243b72;
    color: #fff;
}

.btn-blue:hover {
    background: #1a2c55;
}

.btn-green {
    background: #1da874;
    color: #fff;
}

.btn-green:hover {
    background: #157c56;
}

.btn-red {
    background: #d62828;
    color: #fff;
}

.btn-red:hover {
    background: #a81f1f;
}
</style>
</head>

<body>

<div class="container">
<div class="card">

<h1>🛒 Tu Carrito</h1>

<form action="carrito" method="post">

<table>
<thead>
<tr>
    <th>ID</th>
    <th>Producto</th>
    <th>Precio</th>
    <th>Cantidad</th>
    <th>Total</th>
    <th>Eliminar</th>
</tr>
</thead>

<tbody>
<% for (Producto p : carrito) {
    double precioConvertido = CurrencyService.convertir(p.getPrecio(), moneda);
%>
<tr>
    <td><%= p.getId() %></td>

    <td>
        <div class="producto">
            <img src="<%= request.getContextPath() %>/ImagenProducto?id=<%= p.getId() %>">
            <strong><%= p.getNombre() %></strong>
        </div>
    </td>

    <td><%= String.format("%.2f", precioConvertido) %> <%= moneda %></td>

    <td>
        <input type="number"
               name="cantidad_<%= p.getId() %>"
               value="<%= p.getStock() %>"
               min="1"
               max="<%= carritoDAO.obtenerStockProducto(p.getId()) %>">
    </td>

    <td><%= String.format("%.2f", precioConvertido * p.getStock()) %> <%= moneda %></td>

    <td>
        <a class="btn btn-red"
           href="carrito?accion=eliminar&id=<%= p.getId() %>"
           onclick="return confirm('🗑️ Eliminar este producto del carrito');">
           🗑️
        </a>
    </td>
</tr>
<% } %>
</tbody>

<tfoot>
<tr>
    <td colspan="4">💰 Total General</td>
    <td><%= String.format("%.2f", totalGeneral) %> <%= moneda %></td>
    <td></td>
</tr>
</tfoot>
</table>

<div class="actions">
    <button type="submit" class="btn btn-blue">🔄 Actualizar</button>
    <a href="tienda" class="btn btn-blue">🛍️ Seguir comprando</a>
    <a href="carrito?accion=vaciar" class="btn btn-red"
       onclick="return confirm('🧹 Vaciar todo el carrito');">
       🧹 Vaciar
    </a>
    <a href="carrito?accion=comprar" class="btn btn-green">
       💳 Finalizar compra
    </a>
</div>

</form>
</div>
</div>

</body>
</html>
