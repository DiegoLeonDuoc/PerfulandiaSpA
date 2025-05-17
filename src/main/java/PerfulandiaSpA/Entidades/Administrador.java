package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ADMINISTRADOR")
@DiscriminatorValue("ADMINISTRADOR")
@PrimaryKeyJoinColumn(name = "RUT_USUARIO")
public class Administrador extends Usuario{

}