package ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.repository;


import ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.modelo.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, String> {
    Optional<Cuenta> findByCodigoAndEstado(String codigo, String estado);
}
