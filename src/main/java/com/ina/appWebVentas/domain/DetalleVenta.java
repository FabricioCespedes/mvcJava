package com.ina.appWebVentas.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;

@Data
@Entity
public class DetalleVenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Long idDetalle;

    @NotNull(message = "Por favor, ingresar la cantidad")
    @Min(value = 1, message = "Por favor, ingrese una cantidad valida")
    private int cantidad;

    @NotNull(message = "Debe de indicar el precio de venta")
    @Column(name = "precio_venta")
    private double precio;

    @NotNull(message = "Debe de seleccionar un producto")
    @JoinColumn(name = "id_producto")
    @ManyToOne(optional = false)
    private Producto producto;

    @NotNull(message = "Debe de seleccionar una venta")
    @JoinColumn(name = "id_venta")
    @ManyToOne(optional = false)
    private Venta venta;

}
