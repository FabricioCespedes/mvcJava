package com.ina.appWebVentas.dao;

import com.ina.appWebVentas.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoDao extends JpaRepository<Producto, Long> {

    // Metodo personalizado Spring Data
    public Iterable<Producto> findByDescripcionContains(String descripcion);
}
