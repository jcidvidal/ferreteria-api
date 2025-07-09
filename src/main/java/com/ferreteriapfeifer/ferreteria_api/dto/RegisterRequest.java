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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String role;
 }


