package ec.edu.monster.eureka.saldo.microservice_saldo.ec.edu.monster.servicios;

import ec.edu.monster.eureka.saldo.microservice_saldo.ec.edu.monster.modelo.Cuenta;
import ec.edu.monster.eureka.saldo.microservice_saldo.ec.edu.monster.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EurekaService {

    private final CuentaRepository cuentaRepository;

    public EurekaService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public double consultarSaldo(String cuenta) {
        Optional<Cuenta> cuentaObj = cuentaRepository.findByCodigoAndEstado(cuenta, "ACTIVO");
        if (cuentaObj.isEmpty()) {
            throw new RuntimeException("ERROR, la cuenta no existe o no está activa.");
        }
        return cuentaObj.get().getSaldo();
    }
}