package ec.edu.monster.eureka.deposito.microservice_deposito.ec.edu.monster.repository;

import ec.edu.monster.eureka.deposito.microservice_deposito.ec.edu.monster.modelo.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}