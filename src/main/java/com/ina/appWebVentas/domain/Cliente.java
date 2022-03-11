package com.ina.appWebVentas.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @NotEmpty(message = "El nombre es obligatorio")
    @Column(name = "nombre")
    private String nombre;

    @NotEmpty(message = "El apellido es obligatorio")
    private String apellido;

    @Column(unique = true)
    @Email(message = "Ingrese un correo valido")
    @NotEmpty(message = "Por favor, ingrese el correo")
    private String email;

    private String telefono;

    @Min(value = 0, message = "El limite debe ser mayor o igual a cero")
    @Column(name = "limite_credito")
    @NotNull(message = "Debe de ingresar un limite de credito")
    private double limiteCredito;

}
