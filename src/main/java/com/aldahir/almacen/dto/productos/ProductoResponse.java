package com.aldahir.almacen.dto.productos;

import java.math.BigDecimal;

public record ProductoResponse(
        Long id,
        String nombre,
        String categoria,
        BigDecimal precio,
        Integer Cantidad
) {
}
