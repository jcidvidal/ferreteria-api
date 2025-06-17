package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.dto.PreferenceRequestDTO;

public interface MercadoPagoService {
    String crearPreferencia(PreferenceRequestDTO request) throws Exception;
    String procesarWebhook(Long paymentId) throws Exception;
}
