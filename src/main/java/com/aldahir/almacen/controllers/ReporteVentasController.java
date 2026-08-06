package com.aldahir.almacen.controllers;

import com.aldahir.almacen.dto.ReporteVentasSucursalResponse;
import com.aldahir.almacen.services.venta.VentaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reporte")
@AllArgsConstructor
@Validated
public class ReporteVentasController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<ReporteVentasSucursalResponse>> getReporteVentasSucursal() {
        return ResponseEntity.ok(ventaService.reporteVentasSucursales());
    }
}
