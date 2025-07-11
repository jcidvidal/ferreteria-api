package com.ferreteriapfeifer.ferreteria_api.model;

import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@IgnoreExtraProperties
@Schema(description = "Detalle de un producto en una boleta, incluyendo cantidad y subtotal.")
public class DetalleProducto {

    @NotNull
    @Schema(description = "Producto asociado al detalle")
    private Producto producto;

    @Min(1)
    @Schema(description = "Cantidad de unidades del producto", example = "3")
    private int cantidad;

    @Schema(description = "Subtotal calculado (precio * cantidad)", example = "14970")
    private int subtotal;


    public void calcularSubtotal() {
        this.subtotal = producto.getPrecio() * cantidad;
    }
}
