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
    @Column(name = "ID_HOR", nullable = false)
    private Long id;

    @Column(name = "DIA_SEM", nullable = false)
    private Boolean diaSem = false;

    @Column(name = "HOR_APERTURA", nullable = false)
    private LocalDate horApertura;

    @Column(name = "HOR_CIERRE", nullable = false)
    private LocalDate horCierre;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_SUC", nullable = false)
    private Sucursal idSuc;

}