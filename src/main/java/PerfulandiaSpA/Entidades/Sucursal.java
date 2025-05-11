package PerfulandiaSpA.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SUCURSAL")
public class Sucursal {
    @Id
    @Column(name = "ID_SUC", nullable = false)
    private Long id;

    @Column(name = "NOM_SUC", nullable = false, length = 50)
    private String nomSuc;

    @Column(name = "DIR_SUC", nullable = false, length = 100)
    private String dirSuc;

}