package com.ferreteriapfeifer.ferreteria_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public abstract class Persona {

    private String nombre;
    private String email;
    private String telefono;
    private String contrasena;
}
