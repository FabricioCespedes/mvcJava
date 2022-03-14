package com.ina.appWebVentas.controller;

import com.ina.appWebVentas.domain.Cliente;
import com.ina.appWebVentas.services.ClienteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientesController {

    @Autowired
    private ClienteService servicioCliente;
    
    @GetMapping("/")
    public String inicio() {
        return "index";
    }

    @GetMapping("/clientes")
    public String listaClientes(Model model) {
        List<Cliente> listar = servicioCliente.listar();
        model.addAttribute("clientes",listar);
        return "listaClientes";
    }

}
