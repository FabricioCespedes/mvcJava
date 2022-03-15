package com.ina.appWebVentas.controller;

import com.ina.appWebVentas.domain.Producto;
import com.ina.appWebVentas.services.ProductoService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductosController {

    @Autowired
    private ProductoService servicioProducto;

    @GetMapping("/productos")
    public String listaProductos(Model model) {
        List<Producto> listar = servicioProducto.listar();
        model.addAttribute("productos", listar);
        return "listaProductos";
    }

    @PostMapping("/filtrarProductos")
    public String filtar(String txtDescripcion, Model model) {
        List<Producto> listar = servicioProducto.listar(txtDescripcion);
        model.addAttribute("productos", listar);
        return "listaProductos";
    }

    @GetMapping("/nuevoProducto")
    public String nuevo(Producto producto) {
        return "producto";
    }

    @PostMapping("/guardarProducto")
    public String guardar(@Valid Producto producto, Errors er,RedirectAttributes atts) {
        if (er.hasErrors()) {
            return "producto";
        }
        servicioProducto.guardar(producto);
        String msg = "Producto guardado con exito";
        atts.addFlashAttribute("msg", msg);
        return "redirect:/productos";
    }

    @GetMapping("/editarProducto/{idProducto}")
    public String editar(Producto producto, Model model, RedirectAttributes atts) {
        producto = servicioProducto.obtenerProducto(producto.getIdProducto());
        String msg = "";
        if (producto != null) {
            model.addAttribute("producto", producto);
            return "producto";
        }
        msg = "No se logro cargar el producto";
        atts.addFlashAttribute("msg", msg);
        return "redirect:/listaProductos";
    }

    @GetMapping("/eliminarProducto/{idProducto}")
    public String eliminar(Producto producto, Model model, RedirectAttributes atts) {
        producto = servicioProducto.obtenerProducto(producto.getIdProducto());
        String msg;
        if (producto != null) {
            servicioProducto.eliminar(producto);
            msg = "Producto eliminado con exito";
        } else {
            msg = "No se logro eliminar el producto";

        }
        atts.addFlashAttribute("msg", msg);
        return "redirect:/productos";
    }
}
