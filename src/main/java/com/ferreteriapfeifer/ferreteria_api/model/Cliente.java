package com.ferreteriapfeifer.ferreteria_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representa a un cliente del sistema.")
public class Cliente extends Persona {
    @Schema(description = "ID único del cliente", example = "dcf7-3bcf-9ee4")
    private String idCliente;
}