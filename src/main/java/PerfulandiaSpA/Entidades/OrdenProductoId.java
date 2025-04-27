package PerfulandiaSpA.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrdenProductoId implements Serializable {
    private static final long serialVersionUID = 4253197732675523256L;
    @Column(name = "ORDEN_ID", nullable = false)
    private Long ordenId;

    @Column(name = "PRODUCTO_ID", nullable = false)
    private Long productoId;

    public Long getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Long ordenId) {
        this.ordenId = ordenId;
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
        OrdenProductoId entity = (OrdenProductoId) o;
        return Objects.equals(this.productoId, entity.productoId) &&
                Objects.equals(this.ordenId, entity.ordenId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productoId, ordenId);
    }

}