package PerfulandiaSpA.DTO;

import PerfulandiaSpA.Entidades.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpleadoDTO extends Usuario {
    private Integer idSucursal;
}
