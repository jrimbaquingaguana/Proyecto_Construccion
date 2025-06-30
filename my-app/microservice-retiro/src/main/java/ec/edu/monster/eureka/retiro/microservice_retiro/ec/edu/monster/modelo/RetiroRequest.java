package ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.modelo;

public class RetiroRequest {

    private String cuenta;
    private double importe;
    private String codEmp;  // Código del empleado que realiza la operación

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

    public String getCodEmp() {
        return codEmp;
    }

    public void setCodEmp(String codEmp) {
        this.codEmp = codEmp;
    }
}

