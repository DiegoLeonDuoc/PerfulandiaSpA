package PerfulandiaSpA.DTO;

import PerfulandiaSpA.Entidades.ProductosPedido;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DevolucionDTO {
    private Integer idProductoPedido;
    private String motivoDevo;
    private String estadoDevo;
    private Integer restock;
}
