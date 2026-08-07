package com.aldahir.almacen.mappers;

import com.aldahir.almacen.dto.ventas.VentaRequest;
import com.aldahir.almacen.dto.ventas.VentaResponse;
import com.aldahir.almacen.enums.EstadoVenta;
import com.aldahir.almacen.entities.Sucursal;
import com.aldahir.almacen.entities.Venta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@AllArgsConstructor
public class VentaMapper {

    private final DetalleVentaMapper detalleVentaMapper;

    private final SucursalMapper sucursalMapper;

    public Venta requestAEntidad(VentaRequest request, Sucursal sucursal) {
        if (request == null) return null;
        return Venta.builder()
                .sucursal(sucursal)
                .estadoVenta(EstadoVenta.REGISTRADA)
                .fecha(LocalDate.now())
                .build();
    }

    public VentaResponse entidadAResponse(Venta venta) {
        if (venta == null) return null;

        return new VentaResponse(
                venta.getId(),
                venta.getFecha().toString(),
                venta.getEstadoVenta().toString(),
                sucursalMapper.entidadAResponse(venta.getSucursal()),
                venta.getDetalleVentas().stream().map(detalleVentaMapper::entidadAResponse).toList(),
                venta.calcularTotal()
        );
    }


}
