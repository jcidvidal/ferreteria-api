package com.ferreteriapfeifer.ferreteria_api.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Admin extends Persona {
    private String idAdmin;
}
