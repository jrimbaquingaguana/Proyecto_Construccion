package ec.edu.monster.eureka.movimiento.microservice_movimiento.ec.edu.monster.repository;

import ec.edu.monster.eureka.movimiento.microservice_movimiento.ec.edu.monster.modelo.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, String> {
    // Buscar movimientos por cuenta
    List<Movimiento> findByCuenta(String cuenta);
}

