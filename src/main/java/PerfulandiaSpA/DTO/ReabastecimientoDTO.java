package PerfulandiaSpA.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReabastecimientoDTO {
    private Integer cantProductos;
    private LocalDate fechaReabas;
    private String estadoReabas;
    private Integer idSucursal;
    private Integer idProducto;
    private Integer idProveedor;
}
