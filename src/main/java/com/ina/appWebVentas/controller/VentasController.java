package com.ina.appWebVentas.controller;

import com.ina.appWebVentas.domain.Cliente;
import com.ina.appWebVentas.domain.DetalleVenta;
import com.ina.appWebVentas.domain.Factura;
import com.ina.appWebVentas.domain.Producto;
import com.ina.appWebVentas.services.IClienteService;
import com.ina.appWebVentas.services.IProductoService;
import com.ina.appWebVentas.services.IVentasService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VentasController {

    @Autowired
    private IVentasService ventaService;

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IClienteService clienteService;
    
    
    @PostMapping("/guardarFacturar")
    public String facturar(@Valid Factura factura, Model model, Errors errors) {
        List<DetalleVenta> detalles;
        List<Producto> productos = productoService.listar();
        double total = 0.0;
        if (errors.hasErrors()) {
            detalles = ventaService.listaDetalleVentas(factura.getIdVenta());
            factura.setDetalleVenta(detalles);
            total = detalles.stream()
                    .map(item -> item.getCantidad() * item.getPrecio())
                    .reduce((subtotal, valor) -> subtotal + valor)
                    .orElse(0.0);
            model.addAttribute("productos", productos);
            model.addAttribute("factura", factura);
            model.addAttribute("total", total);
            return "facturar";
        }

        factura = ventaService.guardar(factura);
        factura.setCantidad(0);
        factura.setIdProducto(0);
        factura.setDescripcion("");

        detalles = ventaService.listaDetalleVentas(factura.getIdVenta());
        factura.setDetalleVenta(detalles);
        total = detalles.stream()
                .map(item -> item.getCantidad() * item.getPrecio())
                .reduce((subtotal, valor) -> subtotal + valor)
                .orElse(0.0);
        String msg = "";
        switch (factura.getRetorno()) {
            case 1:
                msg = "La factura esta cancelada no se puede actualizar";
                break;
            case 2:
                msg = "El stock es insuficiente";
                break;
            case 3:
                msg = null;
                break;
            default:
                msg = "Error inesperado";
                break;
        }
        model.addAttribute("productos", productos);
        model.addAttribute("factura", factura);
        model.addAttribute("total", total);
        model.addAttribute("msg", msg);

        return "facturar";
    }

    @GetMapping("/facturar/{idCliente}")
    public String abrirFactura(Cliente cliente, Model model) {
        Factura factura = new Factura();
        factura.setIdCliente(cliente.getIdCliente());
        cliente = clienteService.obtenerCliente(cliente.getIdCliente());
        factura.setNombreCliente(cliente.getNombre()+ " " + cliente.getApellido() );
        
        List<Producto> lista = productoService.listar();
        model.addAttribute("productos",lista);
        model.addAttribute("factura",factura);
        return "facturar";
    }
}
