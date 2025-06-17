package PerfulandiaSpA.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PedidoDTO {
    private Integer idSucursal;
    private LocalDate fecPedido;
    private Integer precioPedido;
    private String metodoPago;
    private String dirEnvio;
    private String dirFacturacion;
    private Integer costoEnvio;
    private String anotaciones;
    private Integer rutCliente;
}