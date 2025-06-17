package PerfulandiaSpA.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CLIENTE")
@PrimaryKeyJoinColumn(name = "RUT_USUARIO")
@DiscriminatorValue("CLIENTE")
public class Cliente extends Usuario{

    @Column(name = "ESTADO_CUENTA", nullable = false, length = 20)
    private String estadoCuenta;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Pedido> pedidos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Carrito> carritos = new ArrayList<>();

}