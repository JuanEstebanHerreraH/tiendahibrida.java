<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Nuevo Producto</title>
</head>
<body>

<h1>Registrar Nuevo Producto</h1>

<form action="guardarProducto" method="post">

    Nombre: <br>
    <input type="text" name="nombre" required><br><br>

    Precio USD: <br>
    <input type="number" name="precio" step="0.01" required><br><br>

    Stock: <br>
    <input type="number" name="stock" required><br><br>

    <button type="submit">Guardar</button>

</form>

</body>
</html>
