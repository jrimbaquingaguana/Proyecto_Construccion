<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Registro de Usuario</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .contenedor-registro {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.2);
            width: 350px;
        }
        h2 {
            margin-bottom: 20px;
            color: #333;
            text-align: center;
        }
        input {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        button {
            background-color: #28a745;
            color: white;
            padding: 10px;
            width: 100%;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #218838;
        }
        .error {
            color: red;
            font-size: 14px;
        }
    </style>
    <script>
        function validarFormulario() {
            const nombre = document.getElementById("nombre").value.trim();
            const usuario = document.getElementById("usuario").value.trim();
            const correo = document.getElementById("correo").value.trim();
            const contraseña = document.getElementById("password").value.trim();
            const errorDiv = document.getElementById("errores");

            errorDiv.innerHTML = "";
            const errores = [];

            if (nombre.length < 3) {
                errores.push("El nombre debe tener al menos 3 caracteres.");
            }
            if (/\d/.test(nombre)) {
                errores.push("El nombre no debe contener números.");
            }
            if (usuario.length < 3) {
                errores.push("El usuario debe tener al menos 3 caracteres.");
            }
            if (!/\S+@\S+\.\S+/.test(correo)) {
                errores.push("El correo no tiene un formato válido.");
            }
            if (contraseña.length < 6) {
                errores.push("La contraseña debe tener al menos 6 caracteres.");
            }

            if (errores.length > 0) {
                errorDiv.innerHTML = errores.join("<br>");
                return false;
            }

            return true;
        }
    </script>
</head>
<body>
    <div class="contenedor-registro">
        <h2>Registro de Usuario</h2>
        <form action="index.jsp" method="post" onsubmit="return validarFormulario()">
            <input type="text" id="nombre" name="nombre" placeholder="Nombre completo" required>
            <input type="text" id="usuario" name="usuario" placeholder="Usuario" required>
            <input type="email" id="correo" name="correo" placeholder="Correo electrónico" required>
            <input type="password" id="password" name="password" placeholder="Contraseña" required>
            <div id="errores" class="error"></div>
            <button type="submit">Registrarse</button>
        </form>
    </div>
</body>
</html>
