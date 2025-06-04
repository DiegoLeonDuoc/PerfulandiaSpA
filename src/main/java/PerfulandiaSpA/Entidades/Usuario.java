package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "USUARIO")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TIPO_USUARIO", discriminatorType = DiscriminatorType.STRING)
public class Usuario {
    @Id
    @Column(name = "RUT_USUARIO", nullable = false)
    private Integer rutUsuario;

    @Column(name = "DV_USUARIO", nullable = false, length = 1)
    private char dvUsuario;

    @Column(name = "NOM_USUARIO", nullable = false, length = 30)
    private String nomUsuario;

    @Column(name = "NOM2_USUARIO", length = 30)
    private String nom2Usuario;

    @Column(name = "APELLIDO_PATERNO", nullable = false, length = 30)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", length = 30)
    private String apellidoMaterno;

    @Column(name = "SEXO_USUARIO", nullable = false, length = 1)
    private char sexoUsuario;

    @Column(name = "DIR_USUARIO", nullable = false, length = 100)
    private String dirUsuario;

    @Column(name = "FECHA_NACIMIENTO", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "TELEFONO_USUARIO", nullable = false, length = 9)
    private String telefonoUsuario;

    @Column(name = "TEL2_USUARIO", length = 9)
    private String tel2Usuario;

    @Column(name = "EMAIL_USUARIO", nullable = false, length = 100)
    private String emailUsuario;

    @Column(name = "PASS_USUARIO", nullable = false, length = 200)
    private String passUsuario;

    @ColumnDefault("'CLIENTE'")
    @Column(name = "TIPO_USUARIO", nullable = false, length = 13, updatable = false, insertable = false)
    private String tipoUsuario;

}