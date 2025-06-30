package ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.controlador;

import ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.modelo.RetiroRequest;
import ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.servicios.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfer")
@CrossOrigin(origins = "http://localhost:8080")  // Permitir CORS para todos los métodos en esta clase

public class CuentaController {

    @Autowired
    private CuentaService cuentaService;
    @CrossOrigin(origins = "http://localhost:8080")  // Aquí especificamos que el frontend puede acceder a este método

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody RetiroRequest retiroRequest) {
        try {
            // Usamos "001" como el código de empleado fijo
            cuentaService.registrarRetiro(retiroRequest.getCuenta(), retiroRequest.getImporte(), "0001");
            return ResponseEntity.ok("Retiro realizado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al realizar el retiro: " + e.getMessage());
        }
    }
}
