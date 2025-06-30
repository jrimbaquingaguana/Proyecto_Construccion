package ec.edu.monster.eureka.movimiento.microservice_movimiento.ec.edu.monster.controlador;

import ec.edu.monster.eureka.movimiento.microservice_movimiento.ec.edu.monster.modelo.Movimiento;
import ec.edu.monster.eureka.movimiento.microservice_movimiento.ec.edu.monster.servicios.EurekaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/api/movimientos")
@RestController
public class EurekaController {

    private final EurekaService eurekaService;

    @Autowired
    public EurekaController(EurekaService eurekaService) {
        this.eurekaService = eurekaService;
    }

    @GetMapping("/movimientos/{cuenta}")
    public List<Movimiento> obtenerMovimientos(@PathVariable String cuenta) {
        return eurekaService.leerMovimientos(cuenta);
    }
}
