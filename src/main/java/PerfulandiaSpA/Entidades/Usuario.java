package PerfulandiaSpA.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "USUARIO")
public class Usuario {
    @Id
    @Column(name = "RUT_US", nullable = false)
    private Integer id;

    @Column(name = "DV_US", nullable = false)
    private Boolean dvUs = false;

    @Column(name = "NOM_US", nullable = false, length = 30)
    private String nomUs;

    @Column(name = "NOM2_US", length = 30)
    private String nom2Us;

    @Column(name = "AP_PAT", nullable = false, length = 30)
    private String apPat;

    @Column(name = "AP_MAT", length = 30)
    private String apMat;

    @Column(name = "SEXO_US", nullable = false)
    private Boolean sexoUs = false;

    @Column(name = "DIR_US", nullable = false, length = 100)
    private String dirUs;

    @Column(name = "FEC_NAC", nullable = false)
    private LocalDate fecNac;

    @Column(name = "TEL1_US", nullable = false)
    private Integer tel1Us;

    @Column(name = "TEL2_US")
    private Integer tel2Us;

    @Column(name = "EMAIL_US", nullable = false, length = 100)
    private String emailUs;

    @Column(name = "PASS_US", nullable = false, length = 200)
    private String passUs;

    @ColumnDefault("'CLIENTE'")
    @Column(name = "TIPO_US", nullable = false, length = 20)
    private String tipoUs;

}