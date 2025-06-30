<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ec.edu.monster.controlador.LoginControlador" %>
<%
    LoginControlador loginControlador = new LoginControlador();
    String mensajeError = "";

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (loginControlador.login(username, password)) {
            session.setAttribute("usuario", username);
            response.sendRedirect("index.jsp");
            return;
        } else {
            mensajeError = "Usuario o contraseña incorrectos.";
        }
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inicio de Sesión</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-size: 40%;
            background-repeat: no-repeat;
            background-position: 1% center;
            display: flex;
            justify-content: flex-start;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .contenedor-login {
            background-color: rgba(255, 255, 255, 0.9);
            padding: 30px;
            border-radius: 19px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
            text-align: center;
            width: 300px;
            margin-left: 700px;
        }
        h1 {
            color: #333;
        }
        input {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        button {
            background-color: #007BFF;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
        }
        button:hover {
            background-color: #0056b3;
        }
        .boton-secundario {
            background-color: #6c757d;
        }
        .boton-secundario:hover {
            background-color: #5a6268;
        }
        #mensaje-error {
            color: red;
            margin-top: 10px;
        }
    </style>
    <script>
        function clearErrorMessage() {
            const mensajeError = document.getElementById('mensaje-error');
            if (mensajeError) {
                mensajeError.textContent = '';
            }
        }

        document.addEventListener('DOMContentLoaded', function () {
            const usernameField = document.querySelector('input[name="username"]');
            const passwordField = document.querySelector('input[name="password"]');

            usernameField.addEventListener('input', clearErrorMessage);
            passwordField.addEventListener('input', clearErrorMessage);
        });
    </script>
</head>
<body>
    <div class="contenedor-login">
        <h1>Iniciar Sesión</h1>
        <form action="login.jsp" method="post">
            <input type="text" name="username" placeholder="Usuario" required>
            <input type="password" name="password" placeholder="Contraseña" required>
            <button type="submit">Iniciar Sesión</button>
        </form>
        <div id="mensaje-error">
            <%= mensajeError %>
        </div>
        <form action="registro.jsp" method="get">
            <button type="submit" class="boton-secundario">¿No tienes cuenta? Regístrate</button>
        </form>
    </div>
</body>
</html>
