package PerfulandiaSpA.Entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "ENVIO")
public class Envio {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_ENVIO", nullable = false)
  private Long id;

  @Column(name = "CODIGO_ENVIO", nullable = false)
  private Long codigoEnvio;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "ID_PEDIDO")
  private Pedido idPedido;

  @Column(name = "FECHA_ENVIO", nullable = false)
  private LocalDate fechaEnvio;

  @Column(name = "FECHA_LLEGADA_ESTIM", nullable = false)
  private LocalDate fechaLlegadaEstim;

  @Column(name = "FECHA_LLEGADA_REAL")
  private LocalDate fechaLlegadaReal;

  @Column(name = "TRANSPORTISTA", nullable = false, length = 30)
  private String transportista;

  @Column(name = "NUM_SEGUIMIENTO", nullable = false)
  private Long numSeguimiento;

  @ColumnDefault("'STANDARD'")
  @Column(name = "METODO_ENVIO", length = 20)
  private String metodoEnvio;

  @Column(name = "COSTO_ENVIO", nullable = false)
  private Long costoEnvio;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "ID_SUCURSAL", nullable = false)
  private Sucursal idSucursal;

  @Column(name = "DIRECCION_ENVIO", nullable = false, length = 200)
  private String direccionEnvio;

  @Column(name = "ANOTACIONES", nullable = false, length = 75)
  private String anotaciones;

}