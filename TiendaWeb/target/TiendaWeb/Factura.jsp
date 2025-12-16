<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.com.tienda.modelo.entidades.Venta, com.com.tienda.modelo.entidades.Producto, com.com.com.tienda.servicios.CurrencyService, com.com.tienda.modelo.entidades.Usuario" %>

<%
Venta venta = (Venta) request.getAttribute("venta");
List<Producto> detalles = (List<Producto>) request.getAttribute("detalles");

// Obtener moneda del usuario o sesión
HttpSession ses = request.getSession(false);
String moneda = "USD";
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
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Factura</title>

<style>
body {
    margin: 0;
    background: #f3f6fb;
    font-family: 'Segoe UI', sans-serif;
}

.factura-box {
    max-width: 900px;
    margin: 40px auto;
    background: white;
    padding: 35px;
    border-radius: 16px;
    box-shadow: 0 12px 35px rgba(0,0,0,.08);
}

.header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    border-bottom: 2px solid #eef2ff;
    padding-bottom: 20px;
    margin-bottom: 25px;
}

.header h2 {
    margin: 0;
    color: #243b72;
    font-size: 32px;
}

.info {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
    gap: 10px;
    margin-bottom: 25px;
    font-size: 15px;
}

.info strong {
    color: #333;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 15px;
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
    background: #f2f6ff;
}

.producto {
    display: flex;
    align-items: center;
    gap: 12px;
}

.producto img {
    width: 70px;
    height: 70px;
    border-radius: 10px;
    object-fit: cover;
}

tfoot td {
    font-size: 20px;
    font-weight: bold;
    background: #e6edff;
    border-top: 2px solid #243b72;
}

.total {
    text-align: right;
    margin-top: 25px;
    font-size: 22px;
    color: #243b72;
}

.actions {
    margin-top: 35px;
    text-align: center;
}

.btn {
    display: inline-block;
    background: #243b72;
    color: white;
    padding: 14px 24px;
    border-radius: 10px;
    text-decoration: none;
    font-weight: 600;
    transition: .25s;
}

.btn:hover {
    background: #1a2c55;
}
</style>
</head>

<body>

<div class="factura-box">

    <div class="header">
        <h2>🧾 Factura de Compra</h2>
        <span>🪙 Moneda: <strong><%= moneda %></strong></span>
    </div>

    <div class="info">
        <div><strong>ID Venta:</strong> <%= venta.getId() %></div>
        <div><strong>ID Cliente:</strong> <%= venta.getIdUsuario() %></div>
        <div><strong>Fecha:</strong> <%= venta.getFecha() %></div>
    </div>

    <table>
        <thead>
            <tr>
                <th>Producto</th>
                <th>Precio</th>
                <th>Cantidad</th>
                <th>Subtotal</th>
            </tr>
        </thead>

        <tbody>
        <%
        double totalConvertido = 0;
        for (Producto p : detalles) {
            double precioConvertido = CurrencyService.convertir(p.getPrecio(), moneda);
            double subtotal = precioConvertido * p.getStock();
            totalConvertido += subtotal;
        %>
            <tr>
                <td>
                    <div class="producto">
                        <img src="<%= request.getContextPath() %>/ImagenProducto?id=<%= p.getId() %>">
                        <strong><%= p.getNombre() %></strong>
                    </div>
                </td>
                <td><%= String.format("%.2f", precioConvertido) %> <%= moneda %></td>
                <td><%= p.getStock() %></td>
                <td><%= String.format("%.2f", subtotal) %> <%= moneda %></td>
            </tr>
        <% } %>
        </tbody>

        <tfoot>
            <tr>
                <td colspan="3">💰 Total</td>
                <td><%= String.format("%.2f", totalConvertido) %> <%= moneda %></td>
            </tr>
        </tfoot>
    </table>

    <div class="actions">
        <a class="btn" href="<%= request.getContextPath() %>/tienda">
            🛍️ Volver a la tienda
        </a>
    </div>

</div>

</body>
</html>
