package com.ina.appWebVentas.services;

import com.ina.appWebVentas.domain.Factura;
import com.ina.appWebVentas.domain.*;
import java.util.Calendar;
import java.util.List;

public interface IVentasService {

    public List<Venta> listaVentas();

    public List<Venta> listaVentas(boolean cancelada);

    public List<Venta> listaVentas(Calendar fecha);

    public List<DetalleVenta> listaDetalleVentas(long idVenta);

    public Venta buscarVenta(Long id);
    
    public DetalleVenta buscarDetalle(Long id);

    public Factura guardar(Factura factura);

    public void eliminar(Venta venta);

    public Factura eliminarDetalle(DetalleVenta detalleVenta);
    
    public int pagarVenta(long idVenta);
    
}
