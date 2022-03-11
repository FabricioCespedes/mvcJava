
package com.ina.appWebVentas.dao;

import com.ina.appWebVentas.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IClienteDao extends JpaRepository<Cliente, Long>{
    
    
    // Metodo personalizado Spring Data
    public Iterable<Cliente> findByNombreContainsOrApellidoContains(String nom, String ape);
    
    
    @Query(value = "Select c from Cliente c where limite_credito >= ?1")
    public Iterable<Cliente> buscarPorLimites(double limite);
}
