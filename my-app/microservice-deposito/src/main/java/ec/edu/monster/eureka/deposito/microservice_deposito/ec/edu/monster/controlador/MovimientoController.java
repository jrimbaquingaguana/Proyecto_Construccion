package ec.edu.monster.eureka.deposito.microservice_deposito.ec.edu.monster.controlador;

import ec.edu.monster.eureka.deposito.microservice_deposito.ec.edu.monster.servicios.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")  // Aquí especificamos que el frontend puede acceder a este método
@RestController
@RequestMapping("/api/transfer")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;
    @CrossOrigin(origins = "http://localhost:8080")  // Aquí especificamos que el frontend puede acceder a este método

    @PostMapping("/deposito")
    public ResponseEntity<String> registrarDeposito(@RequestBody DepositoRequest depositoRequest) {
        try {
            String codEmp = "0001";  // Código del empleado
            double importe = depositoRequest.getImporte(); // Se mantiene como double

            // Llamar al servicio para registrar el depósito
            movimientoService.registrarDeposito(depositoRequest.getCuenta(), importe, codEmp);
            return ResponseEntity.ok("Depósito registrado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // DTO para manejar la solicitud de depósito
    public static class DepositoRequest {
        private String cuenta;
        private double importe;

        // Getters y Setters
        public String getCuenta() {
            return cuenta;
        }

        public void setCuenta(String cuenta) {
            this.cuenta = cuenta;
        }

        public double getImporte() {
            return importe;
        }

        public void setImporte(double importe) {
            this.importe = importe;
        }
    }
}
