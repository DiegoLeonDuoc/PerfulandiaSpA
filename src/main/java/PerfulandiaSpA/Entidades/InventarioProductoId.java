package PerfulandiaSpA.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InventarioProductoId implements Serializable {
    private static final long serialVersionUID = 6385378290899313132L;
    @Column(name = "INVENTARIO_ID", nullable = false)
    private Long inventarioId;

    @Column(name = "PRODUCTO_ID", nullable = false)
    private Long productoId;

    public Long getInventarioId() {
        return inventarioId;
    }

    public void setInventarioId(Long inventarioId) {
        this.inventarioId = inventarioId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InventarioProductoId entity = (InventarioProductoId) o;
        return Objects.equals(this.inventarioId, entity.inventarioId) &&
                Objects.equals(this.productoId, entity.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inventarioId, productoId);
    }

}