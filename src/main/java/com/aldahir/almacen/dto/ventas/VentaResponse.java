package com.aldahir.almacen.dto.ventas;

import com.aldahir.almacen.dto.sucursales.SucursalResponse;

import java.math.BigDecimal;
import java.util.List;

public record VentaResponse(
        Long id,
        String fecha,
        String estado,
        SucursalResponse sucursal,
        List<DetalleVentaResponse> detalles,
        BigDecimal total
) {
}
