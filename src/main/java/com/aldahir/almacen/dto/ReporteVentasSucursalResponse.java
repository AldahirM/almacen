package com.aldahir.almacen.dto;

import java.math.BigDecimal;

public record ReporteVentasSucursalResponse(
        Long idSucursal,
        String nombre,
        BigDecimal totalFacturado,
        Integer cantidadProdVendidos
) {
}
