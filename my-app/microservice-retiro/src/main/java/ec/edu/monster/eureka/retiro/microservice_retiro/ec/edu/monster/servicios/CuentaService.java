package ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.servicios;

import ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.modelo.Cuenta;
import ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.repository.CuentaRepository;
import ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

@Service
public class CuentaService {

    @Autowired
    private DataSource dataSource;  // Utiliza la configuración de la fuente de datos

    public void registrarRetiro(String cuenta, double importe, String codEmp) {
        Connection cn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            // Obtener la conexión
            cn = dataSource.getConnection();
            cn.setAutoCommit(false); // Inicia la transacción

            // Leer datos de la cuenta
            String sql = "SELECT dec_cuensaldo, int_cuencontmov "
                    + "FROM cuenta "
                    + "WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO' "
                    + "FOR UPDATE";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            rs = pstm.executeQuery();

            if (!rs.next()) {
                throw new SQLException("ERROR, la cuenta no existe o no está activa.");
            }

            double saldo = rs.getDouble("dec_cuensaldo");
            int cont = rs.getInt("int_cuencontmov");
            rs.close();
            pstm.close();

            // Verificar saldo suficiente
            if (saldo < importe) {
                throw new SQLException("ERROR, saldo insuficiente.");
            }

            // Actualizar la cuenta
            saldo -= importe;
            cont++;
            sql = "UPDATE cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? "
                    + "WHERE chr_cuencodigo = ? AND vch_cuenestado = 'ACTIVO'";
            pstm = cn.prepareStatement(sql);
            pstm.setDouble(1, saldo);
            pstm.setInt(2, cont);
            pstm.setString(3, cuenta);
            pstm.executeUpdate();
            pstm.close();

            // Registrar movimiento
            sql = "INSERT INTO movimiento(chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte) "
                    + "VALUES (?, ?, SYSDATE(), ?, '004', ?)";
            pstm = cn.prepareStatement(sql);
            pstm.setString(1, cuenta);
            pstm.setInt(2, cont);
            pstm.setString(3, codEmp);
            pstm.setDouble(4, importe);
            pstm.executeUpdate();

            // Confirmar la transacción
            cn.commit();

        } catch (SQLException e) {
            try {
                if (cn != null) {
                    cn.rollback();  // Revertir cambios si ocurre un error
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Error al revertir la transacción", ex);
            }
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstm != null) pstm.close();
                if (cn != null) cn.close();  // Cerrar la conexión
            } catch (SQLException e) {
                throw new RuntimeException("Error al cerrar recursos", e);
            }
        }
    }
}
