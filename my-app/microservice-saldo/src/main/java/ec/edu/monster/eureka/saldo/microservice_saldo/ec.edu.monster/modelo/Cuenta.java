package ec.edu.monster.eureka.saldo.microservice_saldo.ec.edu.monster.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "cuenta")
public class Cuenta {

    @Id
    @Column(name = "chr_cuencodigo")
    private String codigo;

    @Column(name = "dec_cuensaldo")
    private double saldo;

    @Column(name = "vch_cuenestado")
    private String estado;

    // Constructor sin parámetros
    public Cuenta() {}

    // Constructor con parámetros
    public Cuenta(String codigo, double saldo, String estado) {
        this.codigo = codigo;
        this.saldo = saldo;
        this.estado = estado;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
