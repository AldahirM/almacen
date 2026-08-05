package com.aldahir.almacen.services.productos;

import com.aldahir.almacen.dto.productos.ProductoRequest;
import com.aldahir.almacen.dto.productos.ProductoResponse;
import com.aldahir.almacen.entities.Producto;
import com.aldahir.almacen.enums.Categoria;
import com.aldahir.almacen.exceptions.RecursoNoEncontradoException;
import com.aldahir.almacen.mappers.ProductoMapper;
import com.aldahir.almacen.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional // Si los metodos tienen Override no los envolverá y no lo mantendrá como transacción
@Slf4j
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(
            String nombre,
            String categgoria,
            BigDecimal precioMin,
            BigDecimal precioMax
    ) {

        log.info("Listando todos los productos");

        return productoRepository.findAll().stream()
                .map(productoMapper::entidadAResponse)
                // equivalente .map(producto -> productoMapper.entidadAResponse(producto))
                .toList();
    }

    @Override
    public ProductoResponse obtenerPorId(Long id) {
        return productoMapper.entidadAResponse(obtenerProductoOException(id));
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {

        log.info("Registrando nuevo producto");

        Categoria categoria = Categoria.obtenerCategoriaPorDescipcion(request.categoria());

        Producto producto = productoMapper.requestAEntidad(request, categoria);

        productoRepository.save(producto);

        log.info("Nuevo producto {} registrado", producto.getNombre());

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {

        Producto producto = obtenerProductoOException(id);
        Categoria categoria = Categoria.obtenerCategoriaPorDescipcion(request.categoria());
        log.info("Actualizando producto con id: {}", producto.getId());

        producto.actualizar(
                request.nombre(),
                categoria,
                request.precio(),
                request.cantidad()
        );
        productoRepository.save(producto);
        log.info("Producto con id {} actualizado", id);
        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public void eliminar(Long id) {
        Producto producto = obtenerProductoOException(id);
        log.info("Eliminando producto con id {}", id);
        productoRepository.delete(producto);
        log.info("Producto con id {} eliminado", id);
    }

    private Producto obtenerProductoOException(Long id) {
        log.info("Buscando producto con id: {}", id);

        return productoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));

    }

    private Categoria obtenerCategoriaPorDescripcion(String descipcion) {
        return Categoria.obtenerCategoriaPorDescipcion(descipcion.trim());
    }
}
