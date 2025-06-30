package ec.edu.monster.eureka.deposito.microservice_deposito.ec.edu.monster.servicios;

import ec.edu.monster.eureka.deposito.microservice_deposito.ec.edu.monster.repository.MovimientoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void registrarDeposito(String cuenta, double importe, String codEmp) {
        try {
            // Paso 1: Consultar saldo y número de movimientos de la cuenta activa
            String sql = "SELECT dec_cuensaldo, int_cuencontmov FROM cuenta WHERE TRIM(chr_cuencodigo) = :cuenta AND vch_cuenestado = 'ACTIVO' FOR UPDATE";
            Object[] result = (Object[]) entityManager.createNativeQuery(sql)
                    .setParameter("cuenta", cuenta.trim())  // Asegurar que la cuenta esté sin espacios
                    .getSingleResult();

            if (result == null) {
                throw new RuntimeException("ERROR: La cuenta no existe o no está activa.");
            }

            // Paso 2: Obtener saldo y número de movimientos y convertir los valores
            double saldo = ((Number) result[0]).doubleValue();  // Convertir saldo a double
            int cont = ((Number) result[1]).intValue();  // Obtener número de movimientos

            // Asignar el número de movimiento desde 21 si es menor que 21
            if (cont < 21) {
                cont = 30;
            } else {
                // Verificar si el número de movimiento ya existe
                String sqlCheck = "SELECT COUNT(*) FROM movimiento WHERE chr_cuencodigo = :cuenta AND int_movinumero = :cont";
                Long count = ((Number) entityManager.createNativeQuery(sqlCheck)
                        .setParameter("cuenta", cuenta.trim())
                        .setParameter("cont", cont)
                        .getSingleResult()).longValue();

                if (count > 0) {
                    cont++;  // Si el número de movimiento ya existe, incrementa el número
                }
            }

            // Paso 3: Actualizar saldo y número de movimientos en la base de datos
            saldo += importe;  // Sumar el importe al saldo actual
            cont++;  // Incrementar el número de movimientos

            // Actualizar saldo y número de movimientos en la base de datos
            sql = "UPDATE cuenta SET dec_cuensaldo = ?, int_cuencontmov = ? WHERE TRIM(chr_cuencodigo) = ? AND vch_cuenestado = 'ACTIVO'";
            entityManager.createNativeQuery(sql)
                    .setParameter(1, saldo)  // Usar double directamente
                    .setParameter(2, cont)
                    .setParameter(3, cuenta.trim())
                    .executeUpdate();

            // Paso 4: Registrar el movimiento de depósito en la tabla "movimiento"
            sql = "INSERT INTO movimiento (chr_cuencodigo, int_movinumero, dtt_movifecha, chr_emplcodigo, chr_tipocodigo, dec_moviimporte) "
                    + "VALUES (?, ?, SYSDATE(), ?, '003', ?)";
            entityManager.createNativeQuery(sql)
                    .setParameter(1, cuenta.trim())   // Cuenta
                    .setParameter(2, cont)            // Número de movimiento
                    .setParameter(3, codEmp)          // Código del empleado
                    .setParameter(4, importe)         // Importe del depósito (double)
                    .executeUpdate();

        } catch (NoResultException e) {
            throw new RuntimeException("ERROR: La cuenta no fue encontrada o no está activa.");
        } catch (Exception e) {
            throw new RuntimeException("ERROR al procesar el depósito: " + e.getMessage());
        }
    }
}
