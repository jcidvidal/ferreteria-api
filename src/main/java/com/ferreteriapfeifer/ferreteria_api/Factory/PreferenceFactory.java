package com.ferreteriapfeifer.ferreteria_api.Factory;

import com.ferreteriapfeifer.ferreteria_api.dto.PreferenceRequestDTO;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PreferenceFactory {

    public PreferenceRequest createFromDTO(PreferenceRequestDTO dto) {
        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title(dto.getNombreProducto())
                .quantity(dto.getCantidad())
                .unitPrice(BigDecimal.valueOf(dto.getPrecioUnitario()))
                .build();

        return PreferenceRequest.builder()
                .items(List.of(item))
                .externalReference(dto.getIdCompra())
                .build();
    }
}
