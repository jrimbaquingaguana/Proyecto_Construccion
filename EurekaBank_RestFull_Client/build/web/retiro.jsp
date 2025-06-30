<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Retiro</title>
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
            .btn-regresar {
                background-color: transparent;
                border: none;
                cursor: pointer;
                position: fixed;
                top: 10px;
                right: 10px;
            }
            .btn-regresar img {
                width: 20px;
                height: 20px;
            }
            .btn-regresar:hover {
                background-color: red;
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
        <h1>Retiro</h1>

        <!-- Botón Regresar -->


        <!-- Formulario de Retiro -->        
        <div class="formulario">
            <h3>Registrar Retiro</h3>
            <form id="retiroForm">
                <label for="cuentaInput">Cuenta:</label>
                <input type="text" id="cuentaInput" name="cuenta" placeholder="Ingrese el número de cuenta" required>

                <label for="importeInput">Importe:</label>
                <input type="number" id="importeInput" name="importe" step="0.01" placeholder="Ingrese el importe" required>

                <div class="button-container">
                    <button type="submit">Retirar</button>
                </div>
            </form>
            <div id="resultado"></div>
        </div>

        <script>
            $(document).ready(function () {
                $('#retiroForm').submit(async function (event) {
                    event.preventDefault();

                    var cuenta = $('#cuentaInput').val();
                    var importe = parseFloat($('#importeInput').val());

                    var url = 'http://localhost:9090/api/transfer/withdraw'; // Nueva URL base

                    // Validación de campos vacíos
                    if (!cuenta || isNaN(importe) || importe <= 0) {
                        $('#resultado').html('<div class="alert alert-danger">Error: La cuenta o el importe son inválidos.</div>');
                        return;
                    }

                    // Datos a enviar en formato JSON
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
                            $('#resultado').html('<div class="alert alert-success">Retiro realizado correctamente.</div>');
                            $('#retiroForm')[0].reset(); // Limpiar el formulario después del éxito
                        } else {
                            const errorText = await response.text();
                            $('#resultado').html('<div class="alert alert-danger">Error al realizar el retiro: ' + errorText + '</div>');
                        }
                    } catch (error) {
                        $('#resultado').html('<div class="alert alert-danger">Error de conexión: ' + error.message + '</div>');
                    }
                });
            });
        </script>
                                <button class="boton-menu" onclick="window.location.href = 'index.jsp'">Volver al menú</button>

    </body>
</html>
