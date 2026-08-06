package com.aldahir.almacen.repositories;

import com.aldahir.almacen.dto.ReporteVentasSucursalResponse;
import com.aldahir.almacen.dto.ventas.VentaResponse;
import com.aldahir.almacen.entities.Venta;
import com.aldahir.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    VentaResponse findByEstadoVentaAndId(EstadoVenta estadoVenta, Long id);

    @Query("select v from Venta v where v.estadoVenta = :estadoVenta " +
            "order by v.sucursal.id")
    List<Venta> findAllActivasOrderedByIdSucursal(EstadoVenta estadoVenta);
}
