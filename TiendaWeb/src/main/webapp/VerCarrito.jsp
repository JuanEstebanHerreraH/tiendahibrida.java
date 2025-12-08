<%@page import="com.com.tienda.modelo.entidades.Producto"%>
<%@page import="com.com.tienda.modelo.dao.CarritoDAO"%>
<%@page import="java.util.List"%>

<%
    CarritoDAO carritoDAO = new CarritoDAO();
    List<Producto> carrito = (List<Producto>) request.getAttribute("carrito");
    if (carrito == null) {
        carrito = new java.util.ArrayList<>();
    }

    double totalGeneral = 0;
    for (Producto p : carrito) {
        totalGeneral += p.getPrecio() * p.getStock();
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Carrito de Compras</title>
    <style>
        body { background: #f4f4f4; font-family: Arial, sans-serif; padding: 20px; margin: 0; }
        h1 { text-align: center; color: #0077ff; }
        table { width: 80%; margin: 20px auto; border-collapse: collapse; background: white; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        table th, table td { padding: 12px; text-align: center; border-bottom: 1px solid #ddd; }
        table th { background-color: #0077ff; color: white; }
        table tr:hover { background-color: #f1f1f1; }
        table tfoot td { font-weight: bold; background-color: #e8f0ff; }
        input[type="number"] { width: 60px; text-align: center; }
        .btn { display: inline-block; padding: 10px 15px; margin: 10px 5px; background-color: #0077ff; color: white; text-decoration: none; border-radius: 5px; font-weight: bold; transition: background 0.3s; }
        .btn:hover { background-color: #005ace; }
        .btn-danger { background-color: #d10000; }
        .btn-danger:hover { background-color: #a00000; }
        .links-container { text-align: center; margin-top: 20px; }
    </style>
</head>
<body>

<h1>Tu carrito</h1>

<form action="carrito" method="post">
    <table>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th>Total</th>
            <th>Eliminar</th>
        </tr>

        <% for (Producto p : carrito) { %>
        <tr>
            <td><%= p.getId() %></td>
            <td><%= p.getNombre() %></td>
            <td>$<%= p.getPrecio() %></td>
            <td>
                <input type="number" 
                       name="cantidad_<%= p.getId() %>" 
                       value="<%= p.getStock() %>" 
                       min="1" 
                       max="<%= carritoDAO.obtenerStockProducto(p.getId()) %>">
            </td>
            <td>$<%= p.getPrecio() * p.getStock() %></td>
            <td>
                <a href="carrito?accion=eliminar&id=<%= p.getId() %>" 
                   class="btn btn-danger"
                   onclick="return confirm('¿Eliminar este producto del carrito?');">X</a>
            </td>
        </tr>
        <% } %>

        <tfoot>
            <tr>
                <td colspan="4">TOTAL GENERAL</td>
                <td>$<%= totalGeneral %></td>
                <td></td>
            </tr>
        </tfoot>
    </table>

    <div class="links-container">
        <button type="submit" class="btn">Actualizar Carrito</button>
        <a href="logout" class="btn">Cerrar Sesión</a>
        <a href="tienda" class="btn">Regresar a la Tienda</a>
        <a href="carrito?accion=vaciar" class="btn btn-danger">Vaciar carrito</a>
    </div>
</form>

</body>
</html>
