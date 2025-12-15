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

// Obtener usuario y moneda preferida
Usuario u = (Usuario) session.getAttribute("usuario");
String moneda = (u != null && u.getMonedaPreferida() != null) ? u.getMonedaPreferida() : "USD";

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
/* Mantén tu CSS igual */
body { margin: 0; background: #f2f6ff; font-family: 'Segoe UI', sans-serif; }
.container { width: 90%; max-width: 1000px; margin: 40px auto; padding: 20px; }
.card { background: white; padding: 25px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
h1 { text-align: center; color: #243b72; margin-bottom: 25px; font-size: 32px; font-weight: 700; }
table { width: 100%; border-collapse: collapse; margin-top: 10px; }
table thead { background: #243b72; color: white; }
table th, table td { padding: 14px; text-align: center; vertical-align: middle; }
table tbody tr:hover { background: #eef3ff; transition: .2s; }
table img { width: 80px; height: 80px; object-fit: cover; border-radius: 8px; }
input[type="number"] { width: 65px; padding: 6px; border: 1px solid #bbb; border-radius: 6px; text-align: center; }
.btn { display: inline-block; padding: 12px 18px; margin: 10px 8px 0 0; text-decoration: none; border-radius: 8px; font-weight: bold; font-size: 15px; transition: 0.25s; cursor: pointer; }
.btn-blue { background: #243b72; color: white; }
.btn-blue:hover { background: #1a2c55; }
.btn-green { background: #1da874; color: white; }
.btn-green:hover { background: #157c56; }
.btn-red { background: #d62828; color: white; }
.btn-red:hover { background: #a81f1f; }
tfoot td { font-weight: bold; background: #dce5ff; padding: 16px; font-size: 18px; border-top: 2px solid #243b72; }
.actions { margin-top: 25px; text-align: center; }
.producto-nombre { display: flex; align-items: center; gap: 10px; }
</style>

</head>
<body>

<div class="container">
<div class="card">
<h1>🛒 Tu carrito de compras</h1>

<form action="carrito" method="post">

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Producto</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th>Total</th>
            <th>Quitar</th>
        </tr>
    </thead>

    <tbody>
        <% for (Producto p : carrito) { 
               double precioConvertido = CurrencyService.convertir(p.getPrecio(), moneda);
        %>
        <tr>
            <td><%= p.getId() %></td>
            <td>
                <div class="producto-nombre">
                    <img src="<%= request.getContextPath() %>/ImagenProducto?id=<%= p.getId() %>" alt="<%= p.getNombre() %>">
                    <span><%= p.getNombre() %></span>
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
                   onclick="return confirm('¿Seguro que deseas eliminar este producto?');">
                    ❌
                </a>
            </td>
        </tr>
        <% } %>
    </tbody>

    <tfoot>
        <tr>
            <td colspan="4">TOTAL GENERAL</td>
            <td><%= String.format("%.2f", totalGeneral) %> <%= moneda %></td>
            <td></td>
        </tr>
    </tfoot>
</table>

<div class="actions">
    <button type="submit" class="btn btn-blue">Actualizar carrito</button>
    <a href="tienda" class="btn btn-blue">Seguir comprando</a>
    <a href="carrito?accion=vaciar" class="btn btn-red">Vaciar carrito</a>
    <a href="carrito?accion=comprar" class="btn btn-green">Finalizar compra</a>
</div>

</form>
</div>
</div>

</body>
</html>
