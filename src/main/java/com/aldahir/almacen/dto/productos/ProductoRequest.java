package com.aldahir.almacen.dto.productos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductoRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 30, message = "El nombre debe tener entre 5 y 30 caracteres")
        String nombre,

        @NotBlank(message = "La categoria es requerida")
        String categoria,

        @NotNull(message = "La cantidad es requerida")
        @Positive(message = "El precio debe ser positivo")
        BigDecimal precio,

        @NotNull(message = "La cantidad es requerida")
        @Positive(message = "La cantidad debe ser positiva")
        Integer cantidad

) {
}
