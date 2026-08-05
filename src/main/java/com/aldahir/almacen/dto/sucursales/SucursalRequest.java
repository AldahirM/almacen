package com.aldahir.almacen.dto.sucursales;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SucursalRequest(
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 50, message = "El nombre debe de tener entre 5 y 50 caracteres")
        String nombre,

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 10, max = 50, message = "El nombre debe de tener entre 5 y 50 caracteres")
        String direccion
        ) {

}
