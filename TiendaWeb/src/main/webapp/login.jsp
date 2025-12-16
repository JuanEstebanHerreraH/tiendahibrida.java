<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Login</title>

    <style>
        body {
            margin: 0;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #6c5ce7, #74b9ff);
        }

        .card {
            width: 380px;
            background: #ffffff;
            padding: 40px 35px;
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.2);
            animation: fadeIn 0.5s ease;
        }

        h2 {
            margin-bottom: 30px;
            text-align: center;
            font-size: 28px;
            font-weight: 800;
            color: #2d3436;
        }

        .input-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-size: 14px;
            color: #636e72;
            font-weight: 600;
        }

        input {
            width: 100%;
            padding: 12px 14px;
            border-radius: 12px;
            border: 2px solid #dfe6e9;
            font-size: 15px;
            transition: 0.25s;
        }

        input:focus {
            border-color: #6c5ce7;
            box-shadow: 0 0 10px rgba(108,92,231,0.35);
            outline: none;
        }

        button {
            width: 100%;
            padding: 13px;
            border: none;
            border-radius: 14px;
            font-size: 16px;
            font-weight: 700;
            cursor: pointer;
            background: linear-gradient(135deg, #6c5ce7, #5f27cd);
            color: white;
            transition: 0.25s;
        }

        button:hover {
            transform: translateY(-1px);
            box-shadow: 0 8px 20px rgba(108,92,231,0.4);
        }

        .error {
            margin-top: 18px;
            padding: 10px;
            background: #ffe6e6;
            color: #c0392b;
            text-align: center;
            border-radius: 10px;
            font-size: 14px;
            font-weight: 700;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(15px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>

<body>

<div class="card">

    <h2>🔐 Iniciar sesión</h2>

    <form action="login" method="post">

        <div class="input-group">
            <label>📧 Correo</label>
            <input type="email" name="email" placeholder="correo@ejemplo.com" required>
        </div>

        <div class="input-group">
            <label>🔑 Contraseña</label>
            <input type="password" name="password" placeholder="••••••••" required>
        </div>

        <button type="submit">🚀 Entrar</button>
    </form>

    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <div class="error">❌ <%= error %></div>
    <%
        }
    %>

</div>

</body>
</html>
