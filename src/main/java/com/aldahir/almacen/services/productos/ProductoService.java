package com.aldahir.almacen.services.productos;

import com.aldahir.almacen.dto.productos.ProductoRequest;
import com.aldahir.almacen.dto.productos.ProductoResponse;

import java.util.List;

public interface ProductoService {

    List<ProductoResponse> listar();

    ProductoResponse obtenerPorId(Long id);

    ProductoResponse registrar(ProductoRequest request);

    ProductoResponse actualizar(ProductoRequest request, Long id);

    void eliminar(Long id);
}
