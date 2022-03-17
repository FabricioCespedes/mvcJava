package com.ina.appWebVentas.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    private long idVenta;
    private String tipo;
    private Calendar fecha;
        
    private long idProducto;
    
    @NotEmpty(message = "El producto es obligatorio")
    private String descripcion;

    private long idCliente;
    
    @NotEmpty(message = "El cliente es obligatorio")
    private String nombreCliente;
    
    private double precio;

    @NotEmpty(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser superior a cero")
    private int cantidad;

    private int retorno;

    private List<DetalleVenta> detalleVenta;
}
