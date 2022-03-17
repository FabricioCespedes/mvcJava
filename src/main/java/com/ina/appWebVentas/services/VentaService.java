package com.ina.appWebVentas.services;

import com.ina.appWebVentas.domain.Factura;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentaService implements IVentasService {

    @Autowired
    private IVentaDao ventaDao;

    @Autowired
    private IDetalleVentaDao detalleVentaDao;

    @Autowired
    private IProductoDao productoDao;

    @Transactional(readOnly = true)
    @Override
    public List<Venta> listaVentas() {
        return ventaDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Venta> listaVentas(boolean cancelada) {
        return (List<Venta>) ventaDao.findByCancelada(cancelada);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Venta> listaVentas(Calendar fecha) {
        return (List<Venta>) ventaDao.findByFecha(fecha);
    }

    @Transactional(readOnly = true)
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

    @Transactional
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
    @Transactional
    public void eliminar(Venta venta) {
        venta =  ventaDao.findById(venta.getIdVenta()).orElse(null);
        if (venta != null) {
            if (!venta.isCancelada()) {
                ventaDao.delete(venta);
            }
        }
    }

    @Override
    @Transactional
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
    @Transactional
    public int pagarVenta(long idVenta) {        
        return ventaDao.cancelar_factura(idVenta);
    }



}
