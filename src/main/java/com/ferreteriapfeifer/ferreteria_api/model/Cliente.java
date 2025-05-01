package com.ferreteriapfeifer.ferreteria_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Persona {
    private String idCliente;
//TODO: Setear idCliente con uuidv4
}


