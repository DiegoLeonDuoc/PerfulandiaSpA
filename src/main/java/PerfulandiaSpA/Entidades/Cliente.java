package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENTE")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "CORREO", nullable = false, length = 100)
    private String correo;

    @Column(name = "DIRECCION", nullable = false)
    private String direccion;

    @Column(name = "TELEFONO", length = 20)
    private String telefono;
}