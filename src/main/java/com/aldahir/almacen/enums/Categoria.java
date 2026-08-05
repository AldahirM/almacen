package com.aldahir.almacen.enums;

import com.aldahir.almacen.exceptions.RecursoNoEncontradoException;
import com.aldahir.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Categoria {

    ALIMENTO("Alimento"),
    HIGIENE("Higiene"),
    JUGUETE("Juguete"),
    ELECTRONICA("Electrónica"),
    ROPA("Ropa"),
    ACCESORIO("Accesorio"),
    FARMACIA("Farmacia");

    private final String descripcion;

    public static Categoria obtenerCategoriaPorDescipcion(String descipcion) {
        StringCustomUtils.validarNoVacio(descipcion, "La descipción es requerida");
        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descipcion);
        for (Categoria categoria : Categoria.values()) {
            if (StringCustomUtils.quitarAcentos(categoria.descripcion).equalsIgnoreCase(descripcionNormalizada)) {
                return categoria;
            }
        }
        throw new RecursoNoEncontradoException("No existe una categoría con la descripción: " + descipcion);
    }
}
