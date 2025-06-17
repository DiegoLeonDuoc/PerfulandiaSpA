package PerfulandiaSpA.DTO;

import PerfulandiaSpA.Entidades.Sucursal;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EnvioDTO {
    private Long codigoEnvio;
    private Integer idPedido;
    private LocalDate fechaEnvio;
    private LocalDate fechaLlegadaEstim;
    private LocalDate fechaLlegadaReal;
    private String transportista;
    private Long numSeguimiento;
    private String metodoEnvio;
}
