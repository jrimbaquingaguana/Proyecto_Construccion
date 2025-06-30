<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dep�sito</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
            min-height: 100vh;
            padding-top: 100px;
        }
        h1 {
            margin: 20px 0;
            color: #333;
        }
        .formulario {
            margin: 20px 0;
            width: 100%;
            max-width: 600px;
            background-color: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }
        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            background-color: #007BFF;
            color: white;
            cursor: pointer;
            margin: 10px 0;
        }
        .button-container {
            text-align: center;
        }
        button:hover {
            background-color: #0056b3;
        }
        .formulario h3 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }
        #resultado {
            margin-top: 20px;
        }
        .alert {
            padding: 10px;
            margin: 10px 0;
            border-radius: 5px;
            text-align: center;
        }
        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .alert-danger {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <h1>Dep�sito</h1>

    <!-- Formulario de Dep�sito -->
    <div class="formulario">
        <h3>Registrar Dep�sito</h3>
        <form id="depositoForm">
            <label for="cuentaInput">Cuenta:</label>
            <input type="text" id="cuentaInput" name="cuenta" placeholder="Ingrese el n�mero de cuenta" required>

            <label for="importeInput">Importe:</label>
            <input type="number" id="importeInput" name="importe" step="0.01" placeholder="Ingrese el importe" required>

            <div class="button-container">
                <button type="submit">Depositar</button>
            </div>
        </form>
        <div id="resultado"></div>

        <!-- Bot�n para regresar al men� -->
        <div class="button-container">
        </div>
    </div>

    <script>
        $(document).ready(function () {
            $('#depositoForm').submit(async function (event) {
                event.preventDefault();

                var cuenta = $('#cuentaInput').val();
                var importe = parseFloat($('#importeInput').val());

                var url = 'http://localhost:8093/api/transfer/deposito'; // URL actualizada

                if (!cuenta || isNaN(importe) || importe <= 0) {
                    $('#resultado').html('<div class="alert alert-danger">Error: La cuenta o el importe son inv�lidos.</div>');
                    return;
                }

                var data = JSON.stringify({
                    cuenta: cuenta,
                    importe: importe
                });

                try {
                    const response = await fetch(url, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: data
                    });

                    if (response.ok) {
                        const result = await response.text();
                        $('#resultado').html('<div class="alert alert-success">Dep�sito realizado correctamente.</div>');
                        $('#depositoForm')[0].reset();
                    } else {
                        const errorText = await response.text();
                        $('#resultado').html('<div class="alert alert-danger">Error al realizar el dep�sito: ' + errorText + '</div>');
                    }
                } catch (error) {
                    $('#resultado').html('<div class="alert alert-danger">Error de conexi�n: ' + error.message + '</div>');
                }
            });
        });
    </script>
    
                            <button class="boton-menu" onclick="window.location.href = 'index.jsp'">Volver al men�</button>

</body>
</html>
