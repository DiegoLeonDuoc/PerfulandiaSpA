package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;

@Entity
@Table(name="Cliente")
public class Cliente extends Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private String a;


}
