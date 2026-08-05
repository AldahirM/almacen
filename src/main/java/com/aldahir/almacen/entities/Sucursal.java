package com.aldahir.almacen.entities;

import com.aldahir.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SUCURSALES")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUCURSAL")
    private Long id;

    @Column(name = "NOMBRE", length = 50, nullable =  false, unique = true)
    private String nombre;

    @Column(name = "DIRECCION", length = 150, nullable = false)
    private String direccion;

    public void validarDatos(String nombre, String direccion){
        StringCustomUtils.validarTamanio(nombre, 5, 50,
                "El tamaño del nombre debe ser de mínimo 5 y máximo de 50 caracteres");
        StringCustomUtils.validarTamanio(direccion, 10, 50,
                "El tamaño de la direccion debe ser de mínimo 5 y máximo de 50 caracteres");
        StringCustomUtils.validarNoVacio(nombre, "El nombre no debe ser vacío");
        StringCustomUtils.validarNoVacio(direccion, "La dirección no debe ser vacía");
    }

    public void actualizar(String nombre, String direccion){
        validarDatos(nombre, direccion);
        this.nombre = nombre.trim();
        this.direccion = direccion.trim();
    }
}
