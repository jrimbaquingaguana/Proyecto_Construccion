package ec.edu.monster.eureka.deposito.microservice_deposito.ec.edu.monster.modelo;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String cuenta;

    @Column(name = "dec_cuensaldo")
    private BigDecimal saldo;

    @Column(name = "int_cuencontmov")
    private int contadorMovimientos;

    @Column(name = "vch_cuenestado")
    private String estado;
}
