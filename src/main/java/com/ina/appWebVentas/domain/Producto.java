package com.ina.appWebVentas.domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @NotEmpty(message = "La descripcion es obligatoria")
    @Column(name = "descripcion")
    private String descripcion;

    @Min(value = 0, message = "El precio debe ser mayor o igual a cero")
    @Column(name = "precio")
    @NotNull(message = "Debe de ingresar un precio de producto")
    private double precio;

    @Min(value = 0, message = "La cantidad debe ser mayor o igual a cero")
    @Column(name = "existencia")
    @NotNull(message = "Debe de ingresar la cantidad existente del producto")
    private int existencia;
    
    @OneToMany(mappedBy = "producto")
    private List<DetalleVenta> detalleVentas;
}
