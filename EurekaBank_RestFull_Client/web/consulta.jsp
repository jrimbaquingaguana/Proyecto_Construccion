<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Consulta de Movimientos</title>
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
        }

        h1 {
            margin: 20px 0;
            color: #333;
        }

        .formulario {
            margin: 20px 0;
            width: 100%;
            max-width: 600px;
            text-align: center;
        }

        input[type="text"] {
            width: 300px;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: white;
            cursor: pointer;
            margin: 10px 5px;
        }

        button:hover {
            background-color: #0056b3;
        }

        table {
            width: 80%;
            margin: 20px 0;
            border-collapse: collapse;
            text-align: center;
            background-color: white;
        }

        th, td {
            padding: 10px;
            border: 1px solid #ccc;
            color: #333;
        }

        th {
            background-color: #3F51B5;
            color: white;
        }

        .saldo-row {
            background-color: #d4edda;
            font-weight: bold;
        }

        .boton-menu {
            margin: 20px 0;
            padding: 10px 30px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        .boton-menu:hover {
            background-color: #0056b3;
        }
    </style>

    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <!-- jsPDF y autoTable -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.28/jspdf.plugin.autotable.min.js"></script>

    <script>
        $(document).ready(function () {
            $('#consultaMovimientosForm').submit(function (event) {
                event.preventDefault();
                var cuenta = $('#cuentaInput').val();

                var urlMovimientos = 'http://localhost:8091/api/movimientos/movimientos/' + cuenta;

                $('#movimientosTableBody').empty();

                $.ajax({
                    type: 'GET',
                    url: urlMovimientos,
                    dataType: 'json',
                    success: function (response) {
                        response.sort((a, b) => new Date(a.fecha) - new Date(b.fecha));
                        $.each(response, function (index, movimiento) {
                            var row = '<tr>' +
                                '<td>' + movimiento.cuenta + '</td>' +
                                '<td>' + movimiento.nromov + '</td>' +
                                '<td>' + movimiento.fechaAsString + '</td>' +
                                '<td>' + movimiento.tipo + '</td>' +
                                '<td>' + movimiento.accion + '</td>' +
                                '<td>' + movimiento.importe.toFixed(2) + '</td>' +
                                '</tr>';
                            $('#movimientosTableBody').append(row);
                        });

                        obtenerSaldo(urlSaldo);
                    },
                    error: function (xhr, status, error) {
                        console.error('Error al obtener movimientos:', status, error);
                        alert('Error al obtener movimientos. Verifique la consola para más detalles.');
                    }
                });
            });

            function obtenerSaldo(urlSaldo) {
                $.ajax({
                    type: 'GET',
                    url: urlSaldo,
                    dataType: 'json',
                    success: function (response) {
                        var saldoRow = '<tr class="saldo-row">' +
                            '<td colspan="5">Saldo Actual</td>' +
                            '<td>' + parseFloat(response).toFixed(2) + '</td>' +
                            '</tr>';
                        $('#movimientosTableBody').append(saldoRow);
                    },
                    error: function (xhr, status, error) {
                        console.error('Error al obtener saldo:', status, error);
                        alert('Error al obtener saldo. Verifique la consola para más detalles.');
                    }
                });
            }

            $('#limpiarBtn').click(function () {
                $('#cuentaInput').val('');
                $('#movimientosTableBody').empty();
            });

            $('#reporteBtn').click(function () {
                var filas = $('#movimientosTableBody tr');
                if (filas.length === 0) {
                    alert("No hay datos para generar el reporte.");
                    return;
                }

                const { jsPDF } = window.jspdf;
                const doc = new jsPDF();

                doc.setFontSize(16);
                doc.text("Reporte de Movimientos Bancarios", 14, 15);

                const headers = [["Cuenta", "Nro Movimiento", "Fecha", "Tipo", "Acción", "Importe"]];
                const data = [];

                filas.each(function () {
                    const columnas = $(this).find('td');
                    if (!$(this).hasClass('saldo-row')) {
                        const filaData = [];
                        columnas.each(function () {
                            filaData.push($(this).text());
                        });
                        data.push(filaData);
                    }
                });

                // Agrega saldo si existe
                const saldoRow = $('#movimientosTableBody .saldo-row td');
                if (saldoRow.length) {
                    const saldo = $(saldoRow[5]).text();
                    data.push(["", "", "", "", "Saldo Actual", saldo]);
                }

                doc.autoTable({
                    head: headers,
                    body: data,
                    startY: 25
                });

                doc.save("reporte_movimientos.pdf");
            });
        });
    </script>
</head>
<body>
    <h1>Consulta de Movimientos</h1>

    <div class="formulario">
        <form id="consultaMovimientosForm">
            <input type="text" id="cuentaInput" name="cuenta" placeholder="Ingrese número de cuenta" required>
            <button type="button" id="limpiarBtn">Limpiar</button>
            <button type="submit">Consultar</button>
        </form>
    </div>

    <table id="resultados">
        <thead>
            <tr>
                <th>Cuenta</th>
                <th>Número de Movimiento</th>
                <th>Fecha</th>
                <th>Tipo</th>
                <th>Acción</th>
                <th>Importe</th>
            </tr>
        </thead>
        <tbody id="movimientosTableBody">
        </tbody>
    </table>

    <button class="boton-menu" id="reporteBtn">Generar Reporte</button>
    <button class="boton-menu" onclick="window.location.href = 'index.jsp'">Volver al menú</button>
</body>
</html>
