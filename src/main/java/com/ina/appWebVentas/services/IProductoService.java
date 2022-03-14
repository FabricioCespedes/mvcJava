package com.ina.appWebVentas.services;

import com.ina.appWebVentas.domain.Producto;
import java.util.List;


public interface IProductoService {
    
    public void guardar(Producto producto);

    public void eliminar(Producto producto);

    public List<Producto> listar();

    public List<Producto> listar(String descripcion);
    
    public Producto obtenerProducto(Long idProducto);
}
