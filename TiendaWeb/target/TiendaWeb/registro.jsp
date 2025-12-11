<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro de Usuario</title>

    <style>
        body {
            margin: 0;
            padding: 0;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;

            background: linear-gradient(135deg, #81ecec, #74b9ff);
            font-family: "Segoe UI", Arial, sans-serif;
        }

        .form-container {
            width: 380px;
            background: #ffffff;
            padding: 35px 30px;
            border-radius: 16px;
            box-shadow: 0 12px 25px rgba(0,0,0,0.15);
            animation: fadeIn 0.35s ease;
        }

        h2 {
            text-align: center;
            margin-top: 0;
            margin-bottom: 25px;
            font-size: 26px;
            color: #2d3436;
            font-weight: 700;
        }

        label {
            font-size: 14px;
            color: #636e72;
            font-weight: 600;
            margin-top: 15px;
            display: block;
        }

        input, select {
            width: 100%;
            padding: 12px;
            margin-top: 6px;
            border-radius: 10px;
            border: 2px solid #dfe6e9;
            font-size: 15px;
            transition: 0.25s;
        }

        input:focus, select:focus {
            border-color: #0984e3;
            box-shadow: 0 0 8px rgba(9,132,227,0.3);
            outline: none;
        }

        button {
            width: 100%;
            padding: 12px;
            margin-top: 25px;
            background: #0984e3;
            color: white;
            font-size: 16px;
            font-weight: bold;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            transition: 0.25s;
        }

        button:hover {
            background: #0767b3;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(12px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>

<div class="form-container">
    <h2>Registro</h2>

    <form action="registroUsuario" method="post">

        <label>Nombre</label>
        <input type="text" name="nombre" required>

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Contraseña</label>
        <input type="password" name="password" required>

        <label>Moneda preferida</label>
        <select name="moneda_preferida" required>
            <option value="COP">COP</option>
            <option value="USD">USD</option>
            <option value="EUR">EUR</option>
        </select>

        <!-- Por defecto quedará como cliente -->
        <input type="hidden" name="rol" value="cliente">

        <button type="submit">Registrar</button>
    </form>

</div>

</body>
</html>
