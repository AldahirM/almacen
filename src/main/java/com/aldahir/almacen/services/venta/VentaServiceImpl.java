package com.aldahir.almacen.services.venta;

import com.aldahir.almacen.dto.ReporteVentasSucursalResponse;
import com.aldahir.almacen.dto.ventas.VentaRequest;
import com.aldahir.almacen.dto.ventas.VentaResponse;
import com.aldahir.almacen.entities.DetalleVenta;
import com.aldahir.almacen.entities.Producto;
import com.aldahir.almacen.entities.Sucursal;
import com.aldahir.almacen.entities.Venta;
import com.aldahir.almacen.enums.EstadoVenta;
import com.aldahir.almacen.exceptions.RecursoNoEncontradoException;
import com.aldahir.almacen.mappers.DetalleVentaMapper;
import com.aldahir.almacen.mappers.VentaMapper;
import com.aldahir.almacen.repositories.ProductoRepository;
import com.aldahir.almacen.repositories.SucursalRepository;
import com.aldahir.almacen.repositories.VentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;

    private final SucursalRepository sucursalRepository;

    private final ProductoRepository productoRepository;

    private final VentaMapper ventaMapper;

    private final DetalleVentaMapper detalleVentaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listarActivas() {

        log.info("Listando ventas activas");

        return ventaRepository.findAllActivasOrderedByIdSucursal(EstadoVenta.REGISTRADA)
                .stream().map(ventaMapper::entidadAResponse
                ).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listarCanceladas() {
        log.info("Listando ventas canceladas");
        return ventaRepository.findAllActivasOrderedByIdSucursal(EstadoVenta.CANCELADA)
                .stream().map(ventaMapper::entidadAResponse
                ).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponse obtenerPorIdActiva(Long id) {
        log.info("Obteniendo venta activa con id: {}", id);
        return ventaRepository.findByEstadoVentaAndId(EstadoVenta.REGISTRADA, id);
    }

    @Override
    public VentaResponse registrar(VentaRequest request) {
        log.info("Registrando venta");
        log.info("Buscando sucursal");
        Sucursal sucursal = sucursalRepository.findById(request.idSucursal())
                .orElseThrow(() -> new RecursoNoEncontradoException("Sucursal no encontrada"));

        Venta venta = ventaMapper.requestAEntidad(request, sucursal);

        request.productos().forEach(detalleRequest -> {
            log.info("Buscando productos por detalle");
            Producto producto = productoRepository.findById(detalleRequest.idProducto())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

            if (detalleRequest.cantidadProducto() > producto.getCantidad()) {
                throw new IllegalArgumentException("La cantidad de productos de compra no debe ser mayor a la de stock");
            }

            venta.agregarDetalle(detalleVentaMapper.requestAEntidad(detalleRequest, venta, producto, detalleRequest.cantidadProducto()));
            log.info("Actualizando el stock de los productos");
            producto.descontarCantidad(
                    producto.getCantidad() - Integer.parseInt(detalleRequest.cantidadProducto().toString())
            );
            productoRepository.save(producto);
        });

        log.info("Venta registrada");
        Venta ventaGuardada = ventaRepository.save(venta);
        return ventaMapper.entidadAResponse(ventaGuardada);
    }

    @Override
    public VentaResponse cancelar(Long id) {
        log.info("Cancelando venta con id: {}", id);

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new
                        RecursoNoEncontradoException("La venta no existe"));

        venta.getDetalleVentas().forEach(detVenta -> {
            Producto producto = productoRepository.findById(detVenta.getProducto().getId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));


            producto.aumentarCantidad(detVenta.getProducto().getCantidad() + detVenta.getCantidadProducto());

            log.info("Cancellando el stock de los productos");
            productoRepository.save(producto);
        });
        venta.cancelar();

        log.info("Venta cancelada");
        ventaRepository.save(venta);
        return ventaMapper.entidadAResponse(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReporteVentasSucursalResponse> reporteVentasSucursales() {
        log.info("Generando reporte de sucursales");
        List<ReporteVentasSucursalResponse> reporte = new ArrayList<>();
        log.info("Obteniendo ventas activas");
        ventaRepository.findAllActivasOrderedByIdSucursal(EstadoVenta.REGISTRADA)
                .forEach(
                        ven ->
                        {
                            if (!reporte.isEmpty() &&
                                    reporte.get(reporte.size() - 1).idSucursal()
                                            .equals(ven.getSucursal().getId())) {
                                ReporteVentasSucursalResponse anterior = reporte.get(reporte.size() - 1);
                                reporte.remove(reporte.size() - 1);
                                reporte.set(reporte.size() - 1, generarNuevoReporte(
                                        anterior.idSucursal(),
                                        anterior.nombre(),
                                        ven.calcularTotal().add(anterior.totalFacturado()),
                                        ven.getDetalleVentas().stream()
                                                .mapToInt(DetalleVenta::getCantidadProducto)
                                                .sum() + anterior.cantidadProdVendidos()
                                ));
                                return;
                            }
                            reporte.add(generarNuevoReporte(
                                    ven.getSucursal().getId(),
                                    ven.getSucursal().getNombre(),
                                    ven.calcularTotal(),
                                    ven.getDetalleVentas().stream()
                                            .mapToInt(DetalleVenta::getCantidadProducto)
                                            .sum()
                            ));
                        }
                );
        log.info("Reporte generado");
        return reporte;
    }

    private ReporteVentasSucursalResponse generarNuevoReporte(Long idSucursal, String nombre, BigDecimal totalVentas, Integer cantidadProducto) {
        return new ReporteVentasSucursalResponse(
                idSucursal,
                nombre,
                totalVentas,
                cantidadProducto
        );
    }
}
