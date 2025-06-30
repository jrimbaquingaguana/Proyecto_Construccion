package ec.edu.monster.eureka.retiro.microservice_retiro.ec.edu.monster.modelo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "movimiento")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chr_cuencodigo")
    private String cuentaCodigo;

    @Column(name = "int_movinumero")
    private int numeroMovimiento;

    @Column(name = "dtt_movifecha")
    private LocalDateTime fecha;

    @Column(name = "chr_emplcodigo")
    private String empleadoCodigo;

    @Column(name = "chr_tipocodigo")
    private String tipoCodigo;

    @Column(name = "dec_moviimporte")
    private BigDecimal importe;
}

