package com.aldahir.almacen.dto.ventas;

import java.math.BigDecimal;

public record DetalleVentaResponse(
        Long idProducto,
        String nombreProducto,
        Integer cantidadProducto,
        BigDecimal precioProducto,
        BigDecimal subtotal
) {
}
