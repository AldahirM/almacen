package com.aldahir.almacen.services.sucursales;

import com.aldahir.almacen.dto.sucursales.SucursalRequest;
import com.aldahir.almacen.dto.sucursales.SucursalResponse;
import com.aldahir.almacen.entities.Sucursal;

import java.util.List;

public interface SucursalService {

    List<SucursalResponse> listar();

    SucursalResponse obtenerPorId(Long id);

    SucursalResponse registrar(SucursalRequest request);

    SucursalResponse actualizar(SucursalRequest request, Long id);

    void eliminar(Long id);

}
