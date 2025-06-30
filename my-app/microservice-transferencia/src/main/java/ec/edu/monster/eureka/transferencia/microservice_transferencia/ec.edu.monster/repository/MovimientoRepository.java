package ec.edu.monster.eureka.transferencia.microservice_transferencia.ec.edu.monster.repository;


import ec.edu.monster.eureka.transferencia.microservice_transferencia.ec.edu.monster.modelo.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}
