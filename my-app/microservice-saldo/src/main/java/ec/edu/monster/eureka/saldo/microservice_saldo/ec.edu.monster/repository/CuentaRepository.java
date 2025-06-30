package ec.edu.monster.eureka.saldo.microservice_saldo.ec.edu.monster.repository;

import ec.edu.monster.eureka.saldo.microservice_saldo.ec.edu.monster.modelo.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, String> {

    @Query("SELECT c FROM Cuenta c WHERE c.codigo = :codigo AND c.estado = :estado")
    Optional<Cuenta> findByCodigoAndEstado(@Param("codigo") String codigo, @Param("estado") String estado);
}
