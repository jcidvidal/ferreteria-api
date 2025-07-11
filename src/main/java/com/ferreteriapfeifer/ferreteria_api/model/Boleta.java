package com.ferreteriapfeifer.ferreteria_api.model;

import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@IgnoreExtraProperties
@Schema(description = "Entidad que representa una boleta temporal antes de realizar la compra.")
public class Boleta {

    @Schema(description = "ID único de la boleta", example = "b-001x")
    private String idBoleta;

    @NotNull
    @Schema(description = "Cliente que genera la boleta")
    private Cliente cliente;

    @NotNull
    @Schema(description = "Lista de productos con cantidad y subtotal")
    private List<DetalleProducto> detalles = new ArrayList<>();

    @NotNull
    @Schema(description = "Estado actual de la boleta", example = "abierta")
    private String estado;

    @NotNull
    @Schema(description = "Total acumulado de la boleta", example = "18990")
    private int total;

    public void cerrarBoleta() {
        this.estado = "cerrada";
    }


    public void calcularTotal() {
        detalles.forEach(DetalleProducto::calcularSubtotal);
        this.total = detalles.stream().mapToInt(DetalleProducto::getSubtotal).sum();
    }
}
