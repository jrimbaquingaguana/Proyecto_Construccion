package ec.edu.monster.eureka.deposito.microservice_deposito.ec.edu.monster.modelo;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "movimiento")
public class Movimiento {


    @Id
    @Column(name = "chr_cuencodigo")
    private String cuenta;

    @Column(name = "int_movinumero")
    private int numeroMovimiento;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dtt_movifecha")
    private Date fecha;

    @Column(name = "chr_emplcodigo")
    private String codigoEmpleado;

    @Column(name = "chr_tipocodigo")
    private String tipoCodigo;

    @Column(name = "dec_moviimporte")
    private BigDecimal importe;

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public int getNumeroMovimiento() {
        return numeroMovimiento;
    }

    public void setNumeroMovimiento(int numeroMovimiento) {
        this.numeroMovimiento = numeroMovimiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getTipoCodigo() {
        return tipoCodigo;
    }

    public void setTipoCodigo(String tipoCodigo) {
        this.tipoCodigo = tipoCodigo;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    // Getters y Setters
}
