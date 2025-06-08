package com.ferreteriapfeifer.ferreteria_api.service.impl;

import com.ferreteriapfeifer.ferreteria_api.dto.PreferenceRequestDTO;
import com.ferreteriapfeifer.ferreteria_api.service.MercadoPagoService;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class MercadoPagoServiceImpl implements MercadoPagoService {

    @Override
    public String crearPreferencia(PreferenceRequestDTO request) throws Exception {
        try {

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(request.getNombreProducto())
                    .quantity(request.getCantidad())
                    .unitPrice(BigDecimal.valueOf(request.getPrecioUnitario()))
                    .build();


            String externalReference = "ferre-pref-" + UUID.randomUUID();


            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(List.of(item))
                    .externalReference(externalReference)
                    .build();


            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            System.out.println("Preferencia creada con ID: " + preference.getId());
            System.out.println("URL para pagar: " + preference.getInitPoint());
            System.out.println("Referencia externa: " + preference.getExternalReference());



            return preference.getInitPoint();

        } catch (MPApiException apiEx) {
            System.out.println("Error de la API de MercadoPago:");
            System.out.println("Status: " + apiEx.getStatusCode());
            System.out.println("Content: " + apiEx.getApiResponse().getContent());
            throw new Exception("Error de MercadoPago: " + apiEx.getApiResponse().getContent());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Otro error: " + e.getMessage());
        }
    }

}
