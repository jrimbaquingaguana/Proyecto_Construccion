package ec.edu.monster.eureka.transferencia.microservice_transferencia.ec.edu.monster.modelo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "Movimiento")
@Getter
@Setter
public class Movimiento {

    @Id
    @Column(name = "chr_cuencodigo")
    private String cuentaCodigo;

    @Column(name = "int_movinumero")
    private int numeroMovimiento;

    @Column(name = "dtt_movifecha")
    private LocalDateTime fechaMovimiento;

    @Column(name = "chr_emplcodigo")
    private String empleadoCodigo;

    @Column(name = "chr_tipocodigo")
    private String tipoCodigo;

    @Column(name = "dec_moviimporte")
    private double importe;
}
