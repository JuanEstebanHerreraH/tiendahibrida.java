<%@page import="com.com.tienda.modelo.entidades.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Nuevo Producto</title>
<style>
:root {
    --bg: #f0f2f5;
    --card: #ffffff;
    --primary: #4a6cf7;
    --primary-dark: #3854c8;
    --shadow: rgba(0,0,0,0.12);
}
body { margin:0; padding:0; background: var(--bg); font-family: "Segoe UI", Arial, sans-serif; }
.container { max-width:480px; margin:60px auto; background: var(--card); padding:35px; border-radius:18px; box-shadow:0 8px 20px var(--shadow);}
label { display:block; margin-bottom:6px; font-weight:600; }
input[type="text"], input[type="number"], input[type="file"] { width:100%; padding:12px; margin-bottom:18px; border:2px solid #dfe4ea; border-radius:10px; font-size:15px; }
button { width:100%; padding:14px; background:var(--primary); color:white; border:none; border-radius:10px; font-size:17px; font-weight:bold; cursor:pointer; margin-top:10px;}
button:hover { background: var(--primary-dark);}
.preview-img { width:100%; height:200px; object-fit:cover; border-radius:10px; margin-bottom:15px; }
</style>
<script>
function previewImage(input) {
    const preview = document.getElementById('imgPreview');
    if(input.files && input.files[0]){
        const reader = new FileReader();
        reader.onload = e => preview.src = e.target.result;
        reader.readAsDataURL(input.files[0]);
    }
}
</script>
</head>
<body>

<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if(u==null){
        response.sendRedirect("login.jsp");
        return;
    }
%>

<div class="container">
<h1>Nuevo Producto</h1>
<form action="GuardarProducto" method="post" enctype="multipart/form-data">
    <label>Nombre del producto</label>
    <input type="text" name="nombre" required>

    <label>Precio (USD)</label>
    <input type="number" name="precio" step="0.01" value="0" required>

    <label>Stock disponible</label>
    <input type="number" name="stock" value="0" required>

    <label>Categoría</label>
    <input type="text" name="categoria" required>

    <label>Imagen</label>
    <input type="file" name="imagen" accept="image/*" onchange="previewImage(this)">
    <img id="imgPreview" class="preview-img" src="imagenes/default.jpg" alt="Vista previa">

    <button type="submit">Guardar Producto</button>
</form>
</div>

</body>
</html>
