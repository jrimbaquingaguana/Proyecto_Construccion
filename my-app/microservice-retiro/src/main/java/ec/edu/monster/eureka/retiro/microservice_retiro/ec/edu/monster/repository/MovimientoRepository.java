package ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.repository;


import ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.modelo.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}
