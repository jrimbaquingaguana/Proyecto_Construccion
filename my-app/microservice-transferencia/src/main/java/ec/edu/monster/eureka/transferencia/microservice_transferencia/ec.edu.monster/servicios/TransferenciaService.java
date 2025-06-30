package ec.edu.monster.eureka.transferencia.microservice_transferencia.ec.edu.monster.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Map;

@Service
public class TransferenciaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Transactional
    public void registrarTransferencia(String cuentaOrigen, String cuentaDestino, double importe, String codEmp) {
        try {
            System.out.println("Iniciando transferencia de " + importe + " de " + cuentaOrigen + " a " + cuentaDestino);

            // 1. Obtener saldo y contador (último movimiento) de cuenta origen
            String sql = "SELECT dec_cuensaldo, COALESCE(int_cuencontmov, 0) AS int_cuencontmov FROM cuenta WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
            Map<String, Object> cuentaOrigenData = jdbcTemplate.queryForMap(sql, cuentaOrigen);

            double saldoOrigen = ((Number) cuentaOrigenData.get("dec_cuensaldo")).doubleValue();
            int contOrigen = ((Number) cuentaOrigenData.get("int_cuencontmov")).intValue();

            // Obtener máximo int_movinumero en tabla movimiento para cuenta origen
            sql = "SELECT COALESCE(MAX(int_movinumero), 0) FROM movimiento WHERE chr_cuencodigo = ?";
            Integer maxMovOrigen = jdbcTemplate.queryForObject(sql, Integer.class, cuentaOrigen);

            // Aseguramos que contOrigen es al menos maxMovOrigen para evitar duplicados
            contOrigen = Math.max(contOrigen, maxMovOrigen);

            if (saldoOrigen < importe) {
                System.out.println("Saldo insuficiente en cuenta origen");
                throw new SQLException("ERROR: saldo insuficiente en la cuenta de origen.");
            }

            saldoOrigen -= importe;
            contOrigen++; // Incremento llave primaria
            System.out.println("Cuenta origen - saldo actualizado: " + saldoOrigen + ", movimiento nro: " + contOrigen);

            // Insertar movimiento origen
            sql = "INSERT INTO movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte) " +
                    "VALUES (?, ?, NOW(), ?, '009', ?)";
            jdbcTemplate.update(sql, cuentaOrigen, contOrigen, codEmp, importe);

            // Actualizar cuenta origen
            sql = "UPDATE cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? WHERE chr_cuencodigo = ?";
            jdbcTemplate.update(sql, saldoOrigen, contOrigen, cuentaOrigen);

            // Cuenta destino
            sql = "SELECT dec_cuensaldo, COALESCE(int_cuencontmov, 0) AS int_cuencontmov FROM cuenta WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
            Map<String, Object> cuentaDestinoData = jdbcTemplate.queryForMap(sql, cuentaDestino);

            double saldoDestino = ((Number) cuentaDestinoData.get("dec_cuensaldo")).doubleValue();
            int contDestino = ((Number) cuentaDestinoData.get("int_cuencontmov")).intValue();

            // Obtener máximo int_movinumero en tabla movimiento para cuenta destino
            sql = "SELECT COALESCE(MAX(int_movinumero), 0) FROM movimiento WHERE chr_cuencodigo = ?";
            Integer maxMovDestino = jdbcTemplate.queryForObject(sql, Integer.class, cuentaDestino);

            contDestino = Math.max(contDestino, maxMovDestino);

            saldoDestino += importe;
            contDestino++; // Incremento llave primaria
            System.out.println("Cuenta destino - saldo actualizado: " + saldoDestino + ", movimiento nro: " + contDestino);

            // Insertar movimiento destino
            sql = "INSERT INTO movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte) " +
                    "VALUES (?, ?, NOW(), ?, '008', ?)";
            jdbcTemplate.update(sql, cuentaDestino, contDestino, codEmp, importe);

            // Actualizar cuenta destino
            sql = "UPDATE cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? WHERE chr_cuencodigo = ?";
            jdbcTemplate.update(sql, saldoDestino, contDestino, cuentaDestino);

            System.out.println("Transferencia completada correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error en la transferencia: " + e.getMessage());
        }
    }

}
