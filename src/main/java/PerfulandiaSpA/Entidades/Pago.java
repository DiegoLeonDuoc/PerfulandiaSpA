package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PAGO")
public class Pago {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "MONTO", nullable = false)
    private Long monto;

    @Column(name = "METODO", length = 50)
    private String metodo;

    @Column(name = "ESTADO", length = 50)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ORDEN_ID")
    private Orden orden;

}