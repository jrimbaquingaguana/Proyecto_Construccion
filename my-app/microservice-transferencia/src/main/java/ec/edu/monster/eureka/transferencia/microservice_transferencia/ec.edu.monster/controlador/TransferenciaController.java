package ec.edu.monster.eureka.transferencia.microservice_transferencia.ec.edu.monster.controlador;

import ec.edu.monster.eureka.transferencia.microservice_transferencia.ec.edu.monster.servicios.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:8080")  // Permitir solo desde localhost:8080

@RestController
@RequestMapping("/api/transferencias")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;
    @CrossOrigin(origins = "*")


    @PostMapping("/transferencia")
    public ResponseEntity<Map<String, Object>> registrarTransferencia(@RequestBody TransferenciaRequest transferenciaRequest) {

        String codEmp = "0001"; // Código del empleado (puede hacerse dinámico si es necesario)
        Map<String, Object> response = new HashMap<>();

        try {
            transferenciaService.registrarTransferencia(
                    transferenciaRequest.getCuentaOrigen(),
                    transferenciaRequest.getCuentaDestino(),
                    transferenciaRequest.getImporte(),
                    codEmp);
            response.put("estado", 1);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // Mover la clase TransferenciaRequest fuera del método
    public static class TransferenciaRequest {
        private String cuentaOrigen;
        private String cuentaDestino;
        private double importe;

        // Getters y setters
        public String getCuentaOrigen() {
            return cuentaOrigen;
        }

        public void setCuentaOrigen(String cuentaOrigen) {
            this.cuentaOrigen = cuentaOrigen;
        }

        public String getCuentaDestino() {
            return cuentaDestino;
        }

        public void setCuentaDestino(String cuentaDestino) {
            this.cuentaDestino = cuentaDestino;
        }

        public double getImporte() {
            return importe;
        }

        public void setImporte(double importe) {
            this.importe = importe;
        }
    }
}
