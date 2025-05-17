package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "HORARIO_TRABAJO")
public class HorarioTrabajo {
    @Id
    @Column(name = "ID_HORARIO", nullable = false)
    private Integer id;

    @Column(name = "DIA_SEMANA", nullable = false)
    private Boolean diaSemana = false;

    @Column(name = "HORARIO_APERTURA", nullable = false)
    private LocalDate horarioApertura;

    @Column(name = "HORARIO_CIERRE", nullable = false)
    private LocalDate horarioCierre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_SUCURSAL", nullable = false)
    private Sucursal idSucursal;

}