package ec.edu.monster.eureka.movimiento.microservice_movimiento.ec.edu.monster.servicios;

import ec.edu.monster.eureka.movimiento.microservice_movimiento.ec.edu.monster.modelo.Movimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class EurekaService {

    private final DataSource dataSource;

    @Autowired
    public EurekaService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Movimiento> leerMovimientos(String cuenta) {
        List<Movimiento> lista = new ArrayList<>();

        // Verificar que la cuenta no sea nula o vacía
        if (cuenta == null || cuenta.isEmpty()) {
            System.out.println("La cuenta proporcionada es inválida.");
            return lista; // Devolver una lista vacía si la cuenta es inválida
        }

        String sql = "SELECT m.chr_cuencodigo cuenta, " +
                "m.int_movinumero nromov, " +
                "m.dtt_movifecha fecha, " +
                "t.vch_tipodescripcion tipo, " +
                "t.vch_tipoaccion accion, " +
                "m.dec_moviimporte importe " +
                "FROM tipomovimiento t " +
                "INNER JOIN movimiento m ON t.chr_tipocodigo = m.chr_tipocodigo " +
                "WHERE m.chr_cuencodigo = ?";

        try (Connection cn = dataSource.getConnection();
             PreparedStatement pstm = cn.prepareStatement(sql)) {

            // Establecer el valor del parámetro
            pstm.setString(1, cuenta);

            // Ejecutar la consulta y obtener el ResultSet
            try (ResultSet rs = pstm.executeQuery()) {

                // Verificar si el ResultSet tiene alguna fila
                if (!rs.next()) {
                    System.out.println("No se encontraron resultados para la cuenta: " + cuenta);
                    return lista; // Retornar una lista vacía si no hay resultados
                }

                // Iterar sobre los resultados
                do {
                    Movimiento rec = new Movimiento();
                    rec.setCuenta(rs.getString("cuenta"));
                    rec.setNromov(rs.getInt("nromov"));
                    Timestamp timestamp = rs.getTimestamp("fecha");
                    rec.setFecha(timestamp); // Timestamp almacena fecha y hora
                    rec.setTipo(rs.getString("tipo"));
                    rec.setAccion(rs.getString("accion"));
                    rec.setImporte(rs.getDouble("importe"));

                    lista.add(rec);

                    // Verificar que se está agregando el movimiento correctamente
                    System.out.println("Movimiento encontrado: " + rec);
                } while (rs.next()); // Cambié a do-while para asegurarme de que el primer resultado se procese

            }

        } catch (SQLException e) {
            // Capturar errores SQL
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al ejecutar la consulta: " + e.getMessage(), e);
        }

        return lista;
    }

}