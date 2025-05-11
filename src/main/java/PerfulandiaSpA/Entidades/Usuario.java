package PerfulandiaSpA.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "USUARIO")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @Column(name = "rut_us")
    private int rutUs;

    @Column(name = "dv_us", nullable = false)
    private String dvUs;

    @Column(name = "nom_us", nullable = false)
    private String nomUs;

    @Column(name = "nom2_us")
    private String nom2Us;

    @Column(name = "ap_pat", nullable = false)
    private String apPat;

    @Column(name = "ap_mat")
    private String apMat;

    @Column(name = "sexo_us", nullable = false)
    private String sexoUs;

    @Column(name = "dir_us", nullable = false)
    private String dirUs;

    @Column(name = "fec_nac", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecNac;

    @Column(name = "tel1_us", nullable = false)
    private Long tel1Us;

    @Column(name = "tel2_us")
    private Long tel2Us;

    @Column(name = "email_us", nullable = false)
    private String emailUs;

    @Column(name = "pass_us", nullable = false)
    private String passUs;
}