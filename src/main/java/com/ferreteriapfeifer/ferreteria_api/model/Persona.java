package com.ferreteriapfeifer.ferreteria_api.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public abstract class Persona {

    private String nombre;
    private String email;
    private String telefono;
    private String contrasena;
}
