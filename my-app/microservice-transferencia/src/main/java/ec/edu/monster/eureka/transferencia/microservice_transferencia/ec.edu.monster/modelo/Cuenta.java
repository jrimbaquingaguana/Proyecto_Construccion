package ec.edu.monster.eureka.transferencia.microservice_transferencia.ec.edu.monster.modelo;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "Cuenta")
@Getter
@Setter
public class Cuenta {
    @Id
    @Column(name = "chr_cuencodigo")
    private String id;

    @Column(name = "dec_cuensaldo")
    private double saldo;

    @Column(name = "int_cuencontmov")
    private int contadorMovimientos;

    @Column(name = "vch_cuenestado")
    private String estado;
}
