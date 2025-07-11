package com.ferreteriapfeifer.ferreteria_api.model;


import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@IgnoreExtraProperties
@Schema(description = "Entidad que representa un producto disponible en la ferretería.")
public class Producto {

    @Schema(description = "ID único del Producto", example = "a3a5-99b6-7efc")
    private String idProducto;

    @NotNull
    @Schema(description = "Nombre del producto", example = "Taladro Bosch")
    private String nombre;

    @NotNull
    @Schema(description = "Precio del producto (CLP)", example = "59990")
    @PositiveOrZero
    private int precio;

    @NotNull
    @Size(min = 10, max = 200)
    @Schema(description = "Descripción del producto", example = "Taladro inalámbrico 18V, 2 baterías")
    private String descripcion;

    @NotNull
    @Schema(description = "Marca del producto", example = "Bosch")
    private String marca;

    @NotNull
    @Min(0)
    @Schema(description = "Stock disponible", example = "12")
    private int stock;


   public void modificarStock(int cantidadProductos) {
       if (this.stock + cantidadProductos < 0) {
           throw new IllegalArgumentException("El stock no puede ser negativo.");
       }
       this.stock += cantidadProductos;
   }
}
