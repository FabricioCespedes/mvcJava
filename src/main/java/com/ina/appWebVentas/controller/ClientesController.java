package com.ina.appWebVentas.controller;

import com.ina.appWebVentas.domain.Cliente;
import com.ina.appWebVentas.services.ClienteService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientesController {

    @Autowired
    private ClienteService servicioCliente;

    @GetMapping("/clientes")
    public String listaClientes(Model model) {
        List<Cliente> listar = servicioCliente.listar();
        model.addAttribute("clientes", listar);
        return "listaClientes";
    }

    @PostMapping("/filtrarClientes")
    public String filtar(String txtTexto, Model model) {
        List<Cliente> listar = servicioCliente.listar(txtTexto, txtTexto);
        model.addAttribute("clientes", listar);
        return "listaClientes";
    }

    @GetMapping("/nuevoCliente")
    public String nuevo(Cliente cliente) {
        return "cliente";
    }

    @PostMapping("/guardarCliente")
    public String guardar(@Valid Cliente cliente, Errors er) {
        if (er.hasErrors()) {
            return "cliente";
        }

        servicioCliente.guardar(cliente);
        return "redirect:/clientes";
    }

    @GetMapping("/editarCliente/{idCliente}")
    public String editar(Cliente cliente, Model model) {
        cliente = servicioCliente.obtenerCliente(cliente.getIdCliente());
        if (cliente != null) {
            model.addAttribute("cliente", cliente);
            return "cliente";
        }
        List<Cliente> lista = servicioCliente.listar();
        String msg = "No se logro cargar el cliente";
        model.addAttribute("clientes", lista);
        model.addAttribute("msg", msg);
        return "listaClientes";
    }

    @GetMapping("/eliminarCliente")
    public String eliminar(Cliente cliente, Model model) {
        String msg = "";
        int resultado = servicioCliente.eliminar(cliente);
        if (resultado == 0) {
            msg = "No se puede elminar porque tiene ventas asociadas";
        } else {
            msg = "Cliente eliminado";
        }
        List<Cliente> lista = servicioCliente.listar();

        model.addAttribute("clientes", lista);
        model.addAttribute("msg", msg);
        return "listaClientes";
    }
}
