package com.ina.appWebVentas.services;

import com.ina.appWebVentas.dao.IDetalleVentaDao;
import com.ina.appWebVentas.dao.IProductoDao;
import com.ina.appWebVentas.dao.IVentaDao;
import com.ina.appWebVentas.domain.DetalleVenta;
import com.ina.appWebVentas.domain.Producto;
import com.ina.appWebVentas.domain.Venta;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaService implements IVentasService {

    @Autowired
    private IVentaDao ventaDao;

    @Autowired
    private IDetalleVentaDao detalleVentaDao;

    @Autowired
    private IProductoDao productoDao;

    @Override
    public List<Venta> listaVentas() {
        return ventaDao.findAll();
    }

    @Override
    public List<Venta> listaVentas(boolean cancelada) {
        return (List<Venta>) ventaDao.findByCancelada(cancelada);
    }

    @Override
    public List<Venta> listaVentas(Calendar fecha) {
        return (List<Venta>) ventaDao.findByFecha(fecha);
    }

    @Override
    public List<DetalleVenta> listaDetalleVentas(long idVenta) {
        Venta venta = new Venta();
        venta.setIdVenta(idVenta);
        return (List<DetalleVenta>) detalleVentaDao.findByVenta(venta);
    }

    @Override
    public Venta buscarVenta(Long id) {
        return ventaDao.findById(id).orElse(null);
    }

    @Override
    public DetalleVenta buscarDetalle(Long id) {
        return detalleVentaDao.getById(id);
    }

    @Override
    public Factura guardar(Factura factura) {
        Producto producto = productoDao.findById(factura.getIdProducto()).orElse(null);
        if (producto != null) {
            factura.setPrecio(producto.getPrecio());
        }
        HashMap resultado = ventaDao.facturar(factura.getTipo(),
                factura.getIdCliente(),
                factura.getIdProducto(),
                factura.getCantidad(),
                factura.getPrecio(),
                factura.getIdVenta(),
                factura.getRetorno());

        factura.setIdVenta((long) resultado.get("idVenta"));
        factura.setRetorno((int) resultado.get("retorno"));
        return factura;
    }

    @Override
    public void eliminar(Venta venta) {
        venta =  ventaDao.findById(venta.getIdVenta()).orElse(null);
        if (venta != null) {
            if (!venta.isCancelada()) {
                ventaDao.delete(venta);
            }
        }
    }

    @Override
    public Factura eliminarDetalle(DetalleVenta detalleVenta) {
        detalleVenta = detalleVentaDao.findById(detalleVenta.getIdDetalle()).orElse(null);
        Factura factura = new Factura();
        if(detalleVenta != null){
            Venta venta = ventaDao.findById(detalleVenta.getIdDetalle()).orElse(null);
            if (venta != null) {
                factura.setIdVenta(venta.getIdVenta());
                factura.setIdCliente(venta.getCliente().getIdCliente());
                factura.setNombreCliente(venta.getCliente().getNombre() + " " + venta.getCliente().getApellido());
                factura.setTipo(venta.getTipo());
                factura.setFecha(venta.getFecha());
                factura.setDetalleVenta(venta.getDetalleVenta());
            }
            if (!venta.isCancelada()) {
                detalleVentaDao.delete(detalleVenta);
                factura.setDetalleVenta(listaDetalleVentas(venta.getIdVenta()));
            }
        }
        return factura;
    }

    @Override
    public int pagarVenta(long idVenta) {
        
        TAREA!!!
        return 0;
    }



}
