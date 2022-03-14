package com.ina.appWebVentas.services;

import com.ina.appWebVentas.dao.IProductoDao;
import com.ina.appWebVentas.domain.Producto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService implements IProductoService{

    @Autowired
    private IProductoDao productoDao;
    
    @Override
    @Transactional
    public void guardar(Producto producto) {
        productoDao.save(producto);
    }

    @Override
    public void eliminar(Producto producto) {
        productoDao.delete(producto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Producto> listar() {
        return productoDao.findAll();        
    }

    @Override
    public List<Producto> listar(String descripcion) {
        return (List<Producto>) productoDao.findByDescripcionContains(descripcion);
    }


    @Override
    public Producto obtenerProducto(Long idProducto) {
        return productoDao.findById(idProducto).orElse(null);
    }


    
}
