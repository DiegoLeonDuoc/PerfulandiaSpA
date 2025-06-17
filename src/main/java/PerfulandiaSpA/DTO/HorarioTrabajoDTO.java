package PerfulandiaSpA.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class HorarioTrabajoDTO {
    private Integer diaSemana;
    private LocalTime horarioApertura;
    private LocalTime horarioCierre;
    private Integer idSucursal;
}
