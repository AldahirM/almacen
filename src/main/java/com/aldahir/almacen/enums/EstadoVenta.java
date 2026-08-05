package com.aldahir.almacen.enums;

import com.aldahir.almacen.exceptions.RecursoNoEncontradoException;
import com.aldahir.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum EstadoVenta {
    REGISTRADA(1L, "Registrada"),
    CANCELADA(0L, "Cancelada");

    private final Long codigo;

    private final String descripcion;

    public static EstadoVenta obtenerEstadoVentaPorDescipcion(String estadoVenta) {
        StringCustomUtils.validarNoVacio(estadoVenta, "La venta es requerida");
        String ventaNormalizada = StringCustomUtils.quitarAcentos(estadoVenta);

        for (EstadoVenta venta : values()) {
            if (StringCustomUtils.quitarAcentos(venta.descripcion).equalsIgnoreCase(ventaNormalizada)) {
                return venta;
            }
        }
        throw new RecursoNoEncontradoException("No existe una categoría con la descripción: " + estadoVenta);
    }

    public static EstadoVenta obtenerEstadoVentaPorCodigo(Long codigo){
        if (codigo == null || codigo < 0 )
            throw new IllegalArgumentException("El codigo es requerido y  no puede ser negativo");

        for (EstadoVenta venta : values()){
            if(Objects.equals(venta.codigo, codigo))
                return venta;
        }
        throw new RecursoNoEncontradoException("No existe un estado de venta con el código: " + codigo);
    }
}
