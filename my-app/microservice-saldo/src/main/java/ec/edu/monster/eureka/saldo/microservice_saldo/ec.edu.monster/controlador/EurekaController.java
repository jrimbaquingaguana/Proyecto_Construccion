package ec.edu.monster.eureka.saldo.microservice_saldo.ec.edu.monster.controlador;

import ec.edu.monster.eureka.saldo.microservice_saldo.ec.edu.monster.servicios.EurekaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:8080")  // Permitir solo desde localhost:8080

@RestController
public class EurekaController {

    @Autowired
    private EurekaService eurekaService;
    @CrossOrigin(origins = "http://localhost:8080")  // Permitir solo desde localhost:8080

    @GetMapping("/saldo/{cuenta}")
    public double consultarSaldo(@PathVariable String cuenta) {
        return eurekaService.consultarSaldo(cuenta);
    }
}