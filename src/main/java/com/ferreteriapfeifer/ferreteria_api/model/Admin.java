package com.ferreteriapfeifer.ferreteria_api.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Administrador del sistema")
public class Admin extends Persona {

    @Schema(description = "ID único del administrador", example = "a7a8-99b3-7efc")
    private String idAdmin;
}
