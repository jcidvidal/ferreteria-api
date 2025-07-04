package com.ferreteriapfeifer.ferreteria_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private String idCliente;
    private String nombre;
    private String email;
    private String telefono;

}
