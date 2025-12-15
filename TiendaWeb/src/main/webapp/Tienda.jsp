<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.com.tienda.modelo.entidades.Producto" %>
<%@ page import="com.com.com.tienda.servicios.CurrencyService" %>

<%
    HttpSession sesion = request.getSession(false);

    // 🔥 Moneda preferida del usuario o USD por defecto
    String moneda = (sesion != null && sesion.getAttribute("monedaPreferida") != null)
            ? (String) sesion.getAttribute("monedaPreferida")
            : "USD";

    String rol = (sesion != null) ? (String) sesion.getAttribute("rol") : null;
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");

    String[] monedas = { "USD", "COP", "EUR", "ARS", "MXN", "PEN" };
%>


<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Tienda</title>
    <style>
        body { margin: 0; background: #f5f6fa; font-family: 'Segoe UI', sans-serif; }
        header { background: white; padding: 15px 40px; display: flex; justify-content: space-between; align-items: center; box-shadow: 0 2px 10px rgba(0,0,0,0.05); position: sticky; top: 0; z-index: 10; }
        header h2 { margin: 0; color: #0077ff; }
        header a { text-decoration: none; color: #333; margin-left: 20px; font-weight: 600; }
        .productos { display: grid; grid-template-columns: repeat(auto-fill, minmax(230px, 1fr)); gap: 25px; padding: 30px 40px; }
        .card { background: white; border-radius: 12px; padding: 15px; box-shadow: 0 2px 8px rgba(0,0,0,0.06); }
        .card img { width: 100%; height: 180px; object-fit: cover; border-radius: 10px; }
        .nombre { font-size: 17px; font-weight: bold; margin: 10px 0 5px; }
        .precio { color: #0077ff; font-size: 18px; font-weight: bold; margin-bottom: 5px; }
        .stock { font-size: 14px; color: #555; }
    </style>
</head>

<body>

<header>
    <h2>🛒 Tienda Web</h2>

    <div>
        <% if (rol == null) { %>
            <a href="login.jsp">Iniciar sesión</a>
        <% } else { %>
            <a href="logout">Cerrar sesión</a>

            <form action="cambiarMoneda" method="get" style="display:inline;">
                <select name="moneda" onchange="this.form.submit()">
                    <% for (String m : monedas) { %>
                        <option value="<%= m %>" <%= m.equals(moneda) ? "selected" : "" %>>
                            <%= m %>
                        </option>
                    <% } %>
                </select>
            </form>
        <% } %>
    </div>
</header>

<div class="productos">
    <% if (productos == null || productos.isEmpty()) { %>
        <p>No hay productos disponibles.</p>
    <% } else { 
        for (Producto p : productos) { 
            double precioConvertido = CurrencyService.convertir(p.getPrecio(), moneda);
    %>
            <div class="card">
                <img src="ImagenProducto?id=<%= p.getId() %>" alt="Producto">
                <div class="nombre"><%= p.getNombre() %></div>
                <div class="precio">
                    <%= String.format("%.2f", precioConvertido) %> <%= moneda %>
                </div>
                <div class="stock">Stock: <%= p.getStock() %></div>
            </div>
    <%  } 
    } %>
</div>

</body>
</html>
