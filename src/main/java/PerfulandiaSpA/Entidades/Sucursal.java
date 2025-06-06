package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "SUCURSAL")
public class Sucursal {
    @Id
    @Column(name = "ID_SUCURSAL", nullable = false)
    private Integer id;

    @Column(name = "NOMBRE_SUCURSAL", nullable = false, length = 50)
    private String nombreSucursal;

    @Column(name = "DIRECCION_SUCURSAL", nullable = false, length = 100)
    private String direccionSucursal;

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stock> stocks = new ArrayList<>();

    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Reabastecimiento> reabastecimientos = new ArrayList<>();

    @OneToMany(mappedBy = "sucursal")
    private List<HorarioTrabajo> horarios = new ArrayList<>();

    @OneToMany(mappedBy = "sucursalAsociada")
    private List<Empleado> empleados = new ArrayList<>();

    @OneToMany(mappedBy = "sucursal")
    private List<Pedido> pedidos = new ArrayList<>();

}