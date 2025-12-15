<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.com.tienda.modelo.entidades.Venta, com.com.tienda.modelo.entidades.Producto, com.com.com.tienda.servicios.CurrencyService, com.com.tienda.modelo.entidades.Usuario" %>

<%
Venta venta = (Venta) request.getAttribute("venta");
List<Producto> detalles = (List<Producto>) request.getAttribute("detalles");

// Obtener moneda del usuario o sesión
HttpSession ses = request.getSession(false);
String moneda = "USD"; // default
if (ses != null) {
    if (ses.getAttribute("monedaPreferida") != null) {
        moneda = (String) ses.getAttribute("monedaPreferida");
    } else if (ses.getAttribute("usuario") != null) {
        Usuario u = (Usuario) ses.getAttribute("usuario");
        if (u.getMonedaPreferida() != null) {
            moneda = u.getMonedaPreferida();
        }
    }
}
%>

<!DOCTYPE html>
<html>
<head>
<title>Factura</title>
<style>
body { font-family: Arial; background: #f5f5f5; padding: 20px; }
.factura-box { max-width: 800px; margin: auto; background: white; padding: 20px; border-radius: 10px; }
table { width: 100%; border-collapse: collapse; margin-top: 15px; }
table, th, td { border: 1px solid #ccc; }
th, td { padding: 10px; text-align: center; vertical-align: middle; }
h2 { text-align: center; }
.total-box { text-align: right; margin-top: 20px; font-size: 20px; }
a { text-decoration: none; color: white; background: #4CAF50; padding: 10px 15px; border-radius: 5px; }
.producto-img { width: 60px; height: 60px; object-fit: cover; border-radius: 6px; }
.producto-nombre { display: flex; align-items: center; gap: 10px; }
</style>
</head>
<body>

<div class="factura-box">

<h2>Factura de Compra</h2>

<p><strong>ID Venta:</strong> <%= venta.getId() %></p>
<p><strong>Cliente (ID Usuario):</strong> <%= venta.getIdUsuario() %></p>
<p><strong>Fecha:</strong> <%= venta.getFecha() %></p>

<table>
<tr>
    <th>Producto</th>
    <th>Precio (<%= moneda %>)</th>
    <th>Cantidad</th>
    <th>Subtotal (<%= moneda %>)</th>
</tr>

<%
double totalConvertido = 0;
for (Producto p : detalles) { 
    double precioConvertido = CurrencyService.convertir(p.getPrecio(), moneda);
    double subtotalConvertido = precioConvertido * p.getStock();
    totalConvertido += subtotalConvertido;
%>
<tr>
    <td>
        <div class="producto-nombre">
            <img class="producto-img" src="<%= request.getContextPath() %>/ImagenProducto?id=<%= p.getId() %>" alt="<%= p.getNombre() %>">
            <span><%= p.getNombre() %></span>
        </div>
    </td>
    <td><%= String.format("%.2f", precioConvertido) %> <%= moneda %></td>
    <td><%= p.getStock() %></td>
    <td><%= String.format("%.2f", subtotalConvertido) %> <%= moneda %></td>
</tr>
<% } %>
</table>

<div class="total-box">
    <strong>Total: <%= String.format("%.2f", totalConvertido) %> <%= moneda %></strong>
</div>

<br>

<a href="<%= request.getContextPath() %>/tienda">Volver a la tienda</a>

</div>

</body>
</html>
