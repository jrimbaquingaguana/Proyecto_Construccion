package ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.modelo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "cuenta")
public class Cuenta {
    @Id
    @Column(name = "chr_cuencodigo")
    private String codigo;

    @Column(name = "dec_cuensaldo")
    private BigDecimal saldo;

    @Column(name = "int_cuencontmov")
    private int contadorMovimientos;

    @Column(name = "vch_cuenestado")
    private String estado;
}
