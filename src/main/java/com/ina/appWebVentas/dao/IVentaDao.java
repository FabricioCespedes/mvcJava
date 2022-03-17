/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ina.appWebVentas.dao;

import com.ina.appWebVentas.domain.Venta;
import java.util.Calendar;
import java.util.HashMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface IVentaDao extends JpaRepository<Venta, Long>{
    
    
    public Iterable<Venta> findByCancelada(boolean cancelada);
    
    public Iterable<Venta> findByFecha(Calendar fecha);
    
    @Transactional
    @Procedure(name="facturar")
    public HashMap facturar(
            @Param("TIPO") String tipo, 
            @Param("ID_CLIENTE") long idCliente,
            @Param("ID_PRODUCTO") long idProducto,
            @Param("CANTIDAD") int cantidad,
            @Param("PRECIO_VENTA") double precio,
            @Param("ID_VENTA") long idVenta,
            @Param("retorno") int retorno);
    
    @Transactional
    @Procedure(name="[CANCELAR_FACTURA]",outputParameterName = "res")
    Integer cancelar_factura(@Param("id_venta") Long idVenta);
}
