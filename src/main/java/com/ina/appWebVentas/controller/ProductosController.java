package com.ina.appWebVentas.controller;

import com.ina.appWebVentas.domain.Producto;
import com.ina.appWebVentas.services.ProductoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductosController {

    @Autowired
    private ProductoService productoCliente;

    @GetMapping("/productos")
    public String listaProductos(Model model) {
        List<Producto> listar = productoCliente.listar();
        model.addAttribute("productos", listar);
        return "listaProductos";
    }
}
