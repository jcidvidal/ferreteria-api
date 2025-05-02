package com.ferreteriapfeifer.ferreteria_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una compra realizada por un cliente.")
public class Compra implements GeneradorComprobante {

    @Schema(description = "ID único de la compra", example = "cmp-09887ab1")
    private String idCompra;

    @NotNull
    @Schema(description = "Fecha y hora del pago", example = "2025-05-01T14:30:00")
    private LocalDateTime fechaPago;

    @NotNull
    @Schema(description = "Cliente que realiza la compra")
    private Cliente cliente;

    @NotNull
    @Schema(description = "Método de pago utilizado", example = "Tarjeta de crédito")
    private String metodoPago;

    @NotNull
    @Schema(description = "Monto total pagado", example = "34990")
    private int montoPagado;

    @Override
    public String generarComprobante() {
        return "Comprobante generado para la compra: " + idCompra;
    }
}
