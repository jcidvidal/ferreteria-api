package com.ferreteriapfeifer.ferreteria_api.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String rol;
    private String nombre;
    private String telefono;
    private String contrasena;
}
