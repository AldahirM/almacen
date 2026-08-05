package com.aldahir.almacen.services;

import com.aldahir.almacen.dto.ventas.VentaRequest;
import com.aldahir.almacen.dto.ventas.VentaResponse;

import java.util.List;

public interface VentaService {
    List<VentaResponse> listarActivas();

    List<VentaResponse> listarCanceladas();

    VentaResponse obtenerPorIdActiva(Long id);

    VentaResponse registrar(VentaRequest request);

    VentaResponse cancelar(Long id);
}
