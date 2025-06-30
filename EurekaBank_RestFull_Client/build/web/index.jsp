<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Menú Principal</title>
        <style>
            body {
                margin: 0;
                padding: 0;
                font-family: Arial, sans-serif;
                color: black;
                background-size: contain;
                background-repeat: no-repeat;
                background-position: center;
                height: 100vh;
                display: flex;
                flex-direction: column;
                align-items: center; /* Centra el contenido horizontalmente */
                justify-content: center; /* Centra el contenido verticalmente */
            }
            .titulo {
                font-size: 36px;
                font-weight: bold;
                margin-bottom: 20px;
                color: #333;
                text-align: center;
            }
            .menu {
                padding: 30px;
                border-radius: 15px;
                box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
                text-align: center;
                width: 400px;
                margin-top: 20px;
                background-color: #f9f9f9; /* Fondo claro para contraste */
            }
            .menu h1 {
                margin-bottom: 20px;
            }
            .menu p {
                margin-bottom: 30px;
                font-size: 16px;
                color: #555;
            }
            .menu button {
                color: white; /* Texto blanco */
                background-color: #007bff; /* Fondo azul */
                padding: 15px 20px;
                border: none;
                border-radius: 5px;
                font-size: 16px;
                cursor: pointer;
                margin: 10px 0;
                width: 100%;
                transition: background-color 0.3s ease;
            }
            .menu button:hover {
                background-color: #0056b3; /* Fondo más oscuro al pasar el mouse */
            }
        </style>
    </head>
    <body>
        <div class="titulo">Tu BanQuito</div>
        <div class="menu">            
            <form action="consulta.jsp" method="get">
                <button type="submit">Consultar</button>
            </form>
            <form action="transferencia.jsp" method="get">
                <button type="submit">Transferencia</button>
            </form>
            <form action="deposito.jsp" method="get">
                <button type="submit">Depositar</button>
            </form>
            <form action="retiro.jsp" method="get">
                <button type="submit">Retirar</button>
            </form>

            <form action="login.jsp" method="get">
                <button type="submit">Salir</button>
            </form>
        </div>
    </body>
</html>
