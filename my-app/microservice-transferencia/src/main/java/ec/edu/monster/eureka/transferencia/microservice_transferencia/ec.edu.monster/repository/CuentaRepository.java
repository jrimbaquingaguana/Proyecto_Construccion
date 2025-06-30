package ec.edu.monster.eureka.transferencia.microservice_transferencia.ec.edu.monster.repository;


import ec.edu.monster.eureka.transferencia.microservice_transferencia.ec.edu.monster.modelo.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {
    Optional<Cuenta> findByIdAndEstado(String id, String estado);
}
