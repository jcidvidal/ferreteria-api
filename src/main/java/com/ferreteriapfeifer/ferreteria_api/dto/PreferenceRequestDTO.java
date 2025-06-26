package com.ferreteriapfeifer.ferreteria_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferenceRequestDTO {
    private String idCompra;
    private String nombreProducto;
    private Integer cantidad;
    private Float precioUnitario;

}
