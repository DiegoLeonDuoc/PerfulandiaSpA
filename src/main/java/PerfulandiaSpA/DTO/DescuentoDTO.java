package PerfulandiaSpA.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DescuentoDTO {
    private String tipoDescuento;
    private Integer valorDescuento;
    private LocalDate fecIniDescuento;
    private LocalDate fecFinDescuento;
    private Integer idProducto;
}
