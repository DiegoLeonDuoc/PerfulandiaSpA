package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalTime;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "HORARIO_TRABAJO")
public class HorarioTrabajo {
    @Id
    @Column(name = "ID_HORARIO", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "DIA_SEMANA", nullable = false, length = 1)
    private Integer diaSemana;

    @Column(name = "HORARIO_APERTURA", nullable = false)
    private LocalTime horarioApertura;

    @Column(name = "HORARIO_CIERRE", nullable = false)
    private LocalTime horarioCierre;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ID_SUCURSAL", nullable = false)
    private Sucursal sucursal;

    public static final Map<Integer, String> diasSemana = Map.of(
            1, "Lunes",
            2, "Martes",
            3, "Miércoles",
            4, "Jueves",
            5, "Viernes",
            6, "Sábado",
            7, "Domingo"
    );


}