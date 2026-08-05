package com.aldahir.almacen.entities;

import com.aldahir.almacen.enums.Categoria;
import com.aldahir.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCTOS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private long id;

    @Column(name = "NOMBRE", length = 30, nullable = false)
    private String nombre;

    @Column(name = "CATEGORIA", nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    public void aumentarCantidad(int cantidad) {
        if (cantidad < 0) throw new IllegalArgumentException("El cantidad debe ser positiva");
        this.cantidad += cantidad;
    }

    public void descontarCantidad(int cantidad) {
        if (cantidad < 0 || cantidad > this.cantidad)
            throw new IllegalArgumentException("El cantidad debe ser positiva y menor o igual a la cantidad actual");
        this.cantidad -= cantidad;
    }

    public void validarDatos(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad) {
        StringCustomUtils.validarTamanio(nombre, 5, 30,
                "El nombre es requerido y debe ser entre 5 y 30 caracteres");

        if (categoria == null) throw new IllegalArgumentException("El categoria no puede ser null");

        if (precio == null || precio.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("El preico es requerido y debe ser positivo");

        if (cantidad == null || cantidad < 0)
            throw new IllegalArgumentException("La cantidad es requerida y debe ser positiva");
    }

    public void actualizar(String nombre, Categoria categoria, BigDecimal precio, Integer cantidad) {
        validarDatos(nombre, categoria, precio, cantidad);

        this.nombre = nombre.trim();
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }
}
