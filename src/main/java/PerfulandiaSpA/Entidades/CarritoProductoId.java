package PerfulandiaSpA.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CarritoProductoId implements Serializable {
    private static final long serialVersionUID = -6677536149309762905L;
    @Column(name = "CARRITO_ID", nullable = false)
    private Long carritoId;

    @Column(name = "PRODUCTO_ID", nullable = false)
    private Long productoId;

    public Long getCarritoId() {
        return carritoId;
    }

    public void setCarritoId(Long carritoId) {
        this.carritoId = carritoId;
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
        CarritoProductoId entity = (CarritoProductoId) o;
        return Objects.equals(this.carritoId, entity.carritoId) &&
                Objects.equals(this.productoId, entity.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carritoId, productoId);
    }

}