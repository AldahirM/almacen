package com.aldahir.almacen.mappers;

import com.aldahir.almacen.dto.ventas.DetalleVentaRequest;
import com.aldahir.almacen.dto.ventas.DetalleVentaResponse;
import com.aldahir.almacen.entities.DetalleVenta;
import com.aldahir.almacen.entities.Producto;
import com.aldahir.almacen.entities.Venta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DetalleVentaMapper {

    public DetalleVenta requestAEntidad(DetalleVentaRequest request, Venta venta, Producto producto, Long cantidad) {
        if (request == null) return null;

        return DetalleVenta.builder()
                .venta(venta)
                .producto(producto)
                .cantidadProducto(Integer.parseInt(cantidad.toString()))
                .precioProducto(producto.getPrecio())
                .build();
    }

    public DetalleVentaResponse entidadAResponse(DetalleVenta venta) {
        if (venta == null) return null;

        return new DetalleVentaResponse(
                venta.getProducto().getId(),
                venta.getProducto().getNombre(),
                venta.getCantidadProducto(),
                venta.getPrecioProducto(),
                calcularSubtotal(venta.getPrecioProducto(), venta.getCantidadProducto())
        );
    }

    public BigDecimal calcularSubtotal(
            BigDecimal precio, Integer cantidad
    ) {
        if (precio == null || cantidad == null) return BigDecimal.ZERO;
        return precio.multiply(BigDecimal.valueOf(cantidad));
    }
}
