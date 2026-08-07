package com.aldahir.almacen.controllers;

import com.aldahir.almacen.dto.ventas.VentaRequest;
import com.aldahir.almacen.dto.ventas.VentaResponse;
import com.aldahir.almacen.services.venta.VentaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@AllArgsConstructor
@Validated
public class VentaController {

    private final VentaService ventaService;

    @GetMapping("/activas")
    public ResponseEntity<List<VentaResponse>> listarActivas(){
        return ResponseEntity.ok(ventaService.listarActivas());
    }

    @GetMapping("/canceladas")
    public ResponseEntity<List<VentaResponse>> listarCanceladas(){
        return ResponseEntity.ok(ventaService.listarCanceladas());
    }

    @GetMapping("/activas/{id}")
    public ResponseEntity<VentaResponse> obtenerActivaPorId(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(ventaService.obtenerPorIdActiva(id));
    }

    @PostMapping
    public ResponseEntity<VentaResponse> registrar(
            @RequestBody @Valid VentaRequest ventaRequest
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventaService.registrar(ventaRequest));
    }
    @DeleteMapping("/canceladas/{id}")
    public ResponseEntity<VentaResponse> cancelar(@PathVariable Long id){
        return ResponseEntity.ok(ventaService.cancelar(id));
    }

}
