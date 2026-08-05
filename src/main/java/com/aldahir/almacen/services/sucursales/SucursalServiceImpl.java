package com.aldahir.almacen.services.sucursales;

import com.aldahir.almacen.dto.sucursales.SucursalRequest;
import com.aldahir.almacen.dto.sucursales.SucursalResponse;
import com.aldahir.almacen.entities.Sucursal;
import com.aldahir.almacen.exceptions.RecursoNoEncontradoException;
import com.aldahir.almacen.mappers.SucursalMapper;
import com.aldahir.almacen.repositories.SucursalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class SucursalServiceImpl implements SucursalService {

    private final SucursalRepository sucursalRepository;

    private final SucursalMapper sucursalMapper;


    @Override
    @Transactional(readOnly = true)
    public List<SucursalResponse> listar() {
        log.info("Listando todas las sucursales");


        return sucursalRepository.findAll().stream()
                .map(sucursalMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SucursalResponse obtenerPorId(Long id) {
        return  sucursalMapper.entidadAResponse(obtenerSucursalOException(id));
    }

    @Override
    public SucursalResponse registrar(SucursalRequest request) {

        log.info("Registrando nueva sucursal");

        validarDatosUnicos(request);

        Sucursal sucursal = sucursalMapper.requestAEntidad(request);

        sucursalRepository.save(sucursal);

        return sucursalMapper.entidadAResponse(sucursal);
    }

    @Override
    public SucursalResponse actualizar(SucursalRequest request, Long id) {

        Sucursal sucursal = obtenerSucursalOException(id);

        log.info("Actualizando sucursal con id: {}", id);

        validarDatosUnicos(request);

        sucursal.actualizar(
                request.nombre(),
                request.direccion()
        );

        sucursalRepository.save(sucursal);

        log.info("Sucursal  con id {} actualizado", id);

        return sucursalMapper.entidadAResponse(sucursal);
    }

    @Override
    public void eliminar(Long id) {
        Sucursal sucursal = obtenerSucursalOException(id);

        log.info("Eliminando sucursal con id: {}", id);

        sucursalRepository.delete(sucursal);

        log.info("Sucursal  con id {} eliminado", id);
    }

    private Sucursal obtenerSucursalOException(Long id) {
        log.info("Buscando sucursal con id: {}", id);

        return sucursalRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Sucursal no encontrada"));
    }

    private void validarDatosUnicos(SucursalRequest request) {
        log.info("Validando datos únicos...");

        if(sucursalRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre de: " + request.nombre());

    }

    private void validarCambiosUnicos(SucursalRequest request, Long id){
        log.info("Validando cambio único...");

        if(sucursalRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw new IllegalArgumentException("Ya existe una sucursal con el nombre de: " + request.nombre());
    }

}
