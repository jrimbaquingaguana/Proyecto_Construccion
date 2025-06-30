<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Transferencia</title>
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
        <h1>Transferencia</h1>

        <!-- Botón Regresar -->
        </button>

        <!-- Formulario de Transferencia -->        
        <div class="formulario">
            <h3>Realizar Transferencia</h3>
            <form id="transferenciaForm">
                <label for="cuentaOrigen">Cuenta Origen:</label>
                <input type="text" id="cuentaOrigen" name="cuentaOrigen" placeholder="Ingrese el número de cuenta origen" required>

                <label for="cuentaDestino">Cuenta Destino:</label>
                <input type="text" id="cuentaDestino" name="cuentaDestino" placeholder="Ingrese el número de cuenta destino" required>

                <label for="importe">Importe:</label>
                <input type="number" id="importe" name="importe" step="0.01" placeholder="Ingrese el importe" required>

                <div class="button-container">
                    <button type="submit">Transferir</button>
                </div>
            </form>
            <div id="resultado"></div>
        </div>

        <script>
            $(document).ready(function () {
                $('#transferenciaForm').submit(async function (event) {
                    event.preventDefault();

                    var cuentaOrigen = $('#cuentaOrigen').val();
                    var cuentaDestino = $('#cuentaDestino').val();
                    var importe = parseFloat($('#importe').val());

                    // Validaciones
                    if (!cuentaOrigen || !cuentaDestino) {
                        $('#resultado').html('<div class="alert alert-danger">Error: Ambas cuentas son obligatorias.</div>');
                        return;
                    }
                    if (isNaN(importe) || importe <= 0) {
                        $('#resultado').html('<div class="alert alert-danger">Error: El importe debe ser mayor a cero.</div>');
                        return;
                    }

                    var url = 'http://localhost:8094/api/transferencias/transferencia';

                    var data = {
                        cuentaOrigen: cuentaOrigen,
                        cuentaDestino: cuentaDestino,
                        importe: importe
                    };

                    try {
                        // Realizar la solicitud POST
                        const response = await fetch(url, {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: JSON.stringify(data)
                        });

                        // Verificar si la respuesta es exitosa
                        if (!response.ok) {
                            const errorText = await response.text();
                            throw new Error('Error al realizar la transferencia');
                        }

                        // Mostrar mensaje de éxito
                        $('#resultado').html('<div class="alert alert-success">Transferencia realizada correctamente.</div>');
                        $('#transferenciaForm')[0].reset(); // Limpiar el formulario
                    } catch (error) {
                        console.error(error.message);
                        $('#resultado').html('<div class="alert alert-danger">' + error.message + '</div>');
                    }
                });
            });
        </script>
            <button class="boton-menu" onclick="window.location.href = 'index.jsp'">Volver al menú</button>

    </body>
</html>
