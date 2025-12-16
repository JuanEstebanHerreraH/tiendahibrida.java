<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>

<%
    List<String> monedas = new java.util.ArrayList<>();
    monedas.add("COP");
    monedas.add("USD");
    monedas.add("EUR");
    monedas.add("ARS");
    monedas.add("MXN");
%>

<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Registro</title>

<style>
    body {
        margin: 0;
        height: 100vh;
        display: flex;
        justify-content: center;
        align-items: center;
        font-family: "Segoe UI", Arial, sans-serif;
        background: linear-gradient(135deg, #74b9ff, #81ecec);
    }

    .card {
        width: 400px;
        background: #fff;
        padding: 40px 35px;
        border-radius: 20px;
        box-shadow: 0 15px 35px rgba(0,0,0,0.18);
        animation: fadeIn 0.4s ease;
    }

    h2 {
        text-align: center;
        margin-bottom: 28px;
        font-size: 28px;
        font-weight: 800;
        color: #2d3436;
    }

    .input-group {
        margin-bottom: 18px;
    }

    label {
        display: block;
        font-size: 14px;
        font-weight: 600;
        color: #636e72;
        margin-bottom: 6px;
    }

    input, select {
        width: 100%;
        padding: 12px 14px;
        border-radius: 12px;
        border: 2px solid #dfe6e9;
        font-size: 15px;
        transition: 0.25s;
    }

    input:focus, select:focus {
        border-color: #0984e3;
        box-shadow: 0 0 10px rgba(9,132,227,0.35);
        outline: none;
    }

    button {
        width: 100%;
        padding: 14px;
        margin-top: 22px;
        border: none;
        border-radius: 14px;
        font-size: 16px;
        font-weight: 700;
        cursor: pointer;
        background: linear-gradient(135deg, #0984e3, #0570c9);
        color: white;
        transition: 0.25s;
    }

    button:hover {
        transform: translateY(-1px);
        box-shadow: 0 8px 20px rgba(9,132,227,0.4);
    }

    @keyframes fadeIn {
        from { opacity: 0; transform: translateY(14px); }
        to { opacity: 1; transform: translateY(0); }
    }
</style>
</head>

<body>

<div class="card">

    <h2>📝 Crear cuenta</h2>

    <form action="registroUsuario" method="post">

        <div class="input-group">
            <label>👤 Nombre</label>
            <input type="text" name="nombre" placeholder="Tu nombre completo" required>
        </div>

        <div class="input-group">
            <label>📧 Correo electrónico</label>
            <input type="email" name="email" placeholder="correo@ejemplo.com" required>
        </div>

        <div class="input-group">
            <label>🔐 Contraseña</label>
            <input type="password" name="password" placeholder="••••••••" required>
        </div>

        <div class="input-group">
            <label>💱 Moneda preferida</label>
            <select name="moneda_preferida" required>
                <% for (String moneda : monedas) { %>
                    <option value="<%= moneda %>"><%= moneda %></option>
                <% } %>
            </select>
        </div>

        <input type="hidden" name="rol" value="cliente">

        <button type="submit">✨ Registrarme</button>
    </form>

</div>

</body>
</html>
