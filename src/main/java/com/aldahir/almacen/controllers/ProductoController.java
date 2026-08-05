package com.aldahir.almacen.controllers;

import com.aldahir.almacen.dto.productos.ProductoRequest;
import com.aldahir.almacen.dto.productos.ProductoResponse;
import com.aldahir.almacen.services.productos.ProductoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@AllArgsConstructor
@Validated
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) BigDecimal precioMin,
            @RequestParam(required = false) BigDecimal precioMax
    ) {
        return ResponseEntity.ok(productoService.listar(
                nombre, categoria, precioMin, precioMax
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(@PathVariable @Positive(message = "El ID debe ser positivo") Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> registrar(
            @Valid @RequestBody ProductoRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.registrar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id,
            @Valid @RequestBody ProductoRequest request
    ) {
        return ResponseEntity.ok(productoService.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
