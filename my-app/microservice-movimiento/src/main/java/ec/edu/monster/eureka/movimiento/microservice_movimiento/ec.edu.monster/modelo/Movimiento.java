package ec.edu.monster.eureka.movimiento.microservice_movimiento.ec.edu.monster.modelo;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



@Entity
@Table(name = "movimiento")
public class Movimiento {

    @Id
    @Column(name = "cuenta")  // Usar @Column si el nombre en la base de datos es diferente
    private String cuenta;  // Cuenta es la clave primaria

    @Column(name = "nromov")
    private int nromov;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "accion")
    private String accion;

    @Column(name = "importe")
    private double importe;

    @Column(name = "referencia")
    private String referencia;

    // Constructor
    public Movimiento(String cuenta) {
        this.cuenta = cuenta;
    }

    public Movimiento() {
    }

    // Getter y Setter para 'cuenta'
    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    // Getter y Setter para 'nromov'
    public int getNromov() {
        return nromov;
    }

    public void setNromov(int nromov) {
        this.nromov = nromov;
    }

    // Getter y Setter para 'fecha'
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    // Getter y Setter para 'tipo'
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Getter y Setter para 'accion'
    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    // Getter y Setter para 'importe'
    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    // Getter y Setter para 'referencia'
    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    // Método para obtener la fecha como una cadena de texto en el formato deseado
    public String getFechaAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(fecha);
    }

    // Método para convertir una cadena de texto a una fecha
    public void setFechaFromString(String fechaString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.fecha = sdf.parse(fechaString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
