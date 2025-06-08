package com.ferreteriapfeifer.ferreteria_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Petición para agregar un producto a una boleta existente")
public class DetalleRequestDTO {

    @NotBlank
    @Schema(description = "ID del producto a agregar", example = "abc123")
    private String idProducto;

    @NotNull
    @Min(1)
    @Schema(description = "Cantidad de unidades del producto", example = "2")
    private Integer cantidad;
}
