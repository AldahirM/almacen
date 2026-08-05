package com.aldahir.almacen.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "DETALLES_VENTAS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_VENTA")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VENTA", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUCTO", nullable = false)
    private Producto producto;

    @Column(name = "CANTIDAD_PRODUCTO", nullable = false)
    private Integer cantidadProducto;

    @Column(name = "PRECIO_PRODUCTO", nullable = false)
    private BigDecimal precioProducto;

    public void validarDatos(Integer cantidadProducto, BigDecimal precioProducto) {
        if (cantidadProducto == null || cantidadProducto < 1 )
            throw new IllegalArgumentException("La cantidad es requerida, debe ser mayor que 0");

        if (precioProducto == null || precioProducto.intValue() < 0)
            throw new IllegalArgumentException("El precio del producto es requerido y debe ser mayor a 0");

    }

    public void validarCantidadProducto(Integer cantidadProducto, Integer cantidadProductoStock) {
        if(cantidadProducto < cantidadProductoStock )
            throw new IllegalArgumentException("La cantidad de productos de compra no debe ser mayor a la de stock");
    }

    public void actualizarDatos(Integer cantidadProducto, BigDecimal precioProducto, Integer cantidadProductoStock) {
        validarDatos(cantidadProducto, precioProducto);

        validarCantidadProducto(cantidadProducto, cantidadProductoStock);


    }
}
