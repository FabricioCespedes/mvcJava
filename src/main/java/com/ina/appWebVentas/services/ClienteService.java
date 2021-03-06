package com.ina.appWebVentas.services;

import com.ina.appWebVentas.dao.IClienteDao;
import com.ina.appWebVentas.domain.Cliente;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService implements IClienteService{

    @Autowired
    private IClienteDao clienteDao;
    
    @Override
    @Transactional
    public void guardar(Cliente cliente) {
        clienteDao.save(cliente);
    }

    @Override
    public Integer eliminar(Cliente cliente) {
        return clienteDao.eliminar_Cliente(cliente.getIdCliente());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Cliente> listar() {
        return clienteDao.findAll();        
    }

    @Override
    public List<Cliente> listar(String nombre, String apellido) {
        return (List<Cliente>) clienteDao.findByNombreContainsOrApellidoContains(nombre, apellido);
    }

    @Override
    public List<Cliente> listar(double limite) {
        return (List<Cliente>) clienteDao.buscarPorLimites(limite);
    }

    @Override
    public Cliente obtenerCliente(Long idCliente) {
        return clienteDao.findById(idCliente).orElse(null);
    }
    
}
