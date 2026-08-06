package com.aldahir.almacen.utils;

import com.aldahir.almacen.entities.Producto;
import com.aldahir.almacen.enums.Categoria;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EpecificacionesFiltros {
    public static Specification<Producto> conFiltros(String nombre, Categoria categoria, BigDecimal precioMin, BigDecimal precioMax) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nombre != null || nombre == "") {
                predicates.add(criteriaBuilder.equal(root.get("nombre"), nombre));
            }
            if (categoria != null) {
                predicates.add(criteriaBuilder.equal(root.get("categoria"), categoria));
            }
            if (precioMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("precio"), precioMin));
            }
            if (precioMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("precio"), precioMax));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
