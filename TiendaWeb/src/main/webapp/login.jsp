<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Iniciar Sesión</title>

    <style>
        body { 
            margin: 0;
            padding: 0;
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;

            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(135deg, #74b9ff, #a29bfe);
        }

        .card {
            width: 380px;
            background: #ffffff;
            padding: 40px 35px;
            border-radius: 18px;
            box-shadow: 0 12px 25px rgba(0,0,0,0.15);
            animation: fadeIn 0.4s ease;
        }

        h2 {
            margin: 0;
            margin-bottom: 25px;
            font-weight: 700;
            font-size: 26px;
            color: #2d3436;
            text-align: center;
        }

        label {
            font-size: 14px;
            color: #636e72;
            margin-bottom: 5px;
            display: block;
        }

        input {
            width: 100%;
            padding: 12px;
            border-radius: 10px;
            border: 2px solid #dfe6e9;
            font-size: 15px;
            margin-bottom: 18px;
            transition: 0.25s;
        }

        input:focus {
            border-color: #6c5ce7;
            box-shadow: 0 0 8px rgba(108, 92, 231, 0.3);
            outline: none;
        }

        button {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            transition: 0.25s;
            background: #6c5ce7;
            color: white;
        }

        button:hover {
            background: #5a4bd1;
        }

        .error {
            margin-top: 15px;
            color: #d63031;
            text-align: center;
            font-size: 14px;
            font-weight: bold;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(12px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>

</head>
<body>

    <div class="card">
        <h2>Iniciar Sesión</h2>

        <form action="login" method="post">

            <label>Correo</label>
            <input type="email" name="email" placeholder="Tu correo" required>

            <label>Contraseña</label>
            <input type="password" name="password" placeholder="Tu contraseña" required>

            <button type="submit">Entrar</button>
        </form>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <div class="error"><%= error %></div>
        <%
            }
        %>
    </div>

</body>
</html>
