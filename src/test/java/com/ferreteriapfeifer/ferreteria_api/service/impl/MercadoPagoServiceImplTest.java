package com.ferreteriapfeifer.ferreteria_api.service.impl;

import com.ferreteriapfeifer.ferreteria_api.Factory.PreferenceFactory;
import com.ferreteriapfeifer.ferreteria_api.dto.PreferenceRequestDTO;
import com.ferreteriapfeifer.ferreteria_api.model.Compra;
import com.ferreteriapfeifer.ferreteria_api.model.Pago;
import com.ferreteriapfeifer.ferreteria_api.repository.PagoRepository;
import com.ferreteriapfeifer.ferreteria_api.service.CompraService;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MercadoPagoServiceImplTest {

    @Mock private PagoRepository pagoRepository;
    @Mock private CompraService compraService;
    @Mock private PreferenceClient preferenceClient;
    @Mock private PreferenceFactory preferenceFactory;
    @Mock private PaymentClient paymentClient;

    private MercadoPagoServiceImpl mercadoPagoService;


    @BeforeEach
    void setUp() {
        mercadoPagoService = new MercadoPagoServiceImpl(
                pagoRepository,
                compraService,
                preferenceFactory,
                preferenceClient,
                paymentClient
        );
    }

    @Test
    void procesarWebhook_deberiaActualizarCompraYGuardarPago() throws Exception {
        // Given
        Long paymentId = 12345L;
        String externalRef = "compra123";

        Payment payment = mock(Payment.class);
        when(paymentClient.get(paymentId)).thenReturn(payment);
        when(payment.getId()).thenReturn(paymentId);
        when(payment.getExternalReference()).thenReturn(externalRef);
        when(payment.getStatus()).thenReturn("approved");
        when(payment.getPaymentTypeId()).thenReturn("credit_card");
        when(payment.getPaymentMethodId()).thenReturn("visa");

        Compra compra = new Compra();
        compra.setIdCompra(externalRef);
        when(compraService.obtenerIdCompra(externalRef)).thenReturn(compra);

        // When
        String resultado = mercadoPagoService.procesarWebhook(paymentId);

        // Then
        assertEquals("Pago procesado correctamente", resultado);
        verify(pagoRepository).guardarPago(any(Pago.class));
        verify(compraService).registrarCompra(compra);
    }

    @Test
    void crearPreferencia_deberiaRetornarUrlPago() throws Exception {
        // Given
        PreferenceRequestDTO dto = new PreferenceRequestDTO("compra123", "Martillo", 2, 5000f);

        PreferenceItemRequest item = PreferenceItemRequest.builder()
                .title(dto.getNombreProducto())
                .quantity(dto.getCantidad())
                .unitPrice(BigDecimal.valueOf(dto.getPrecioUnitario()))
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(List.of(item))
                .externalReference(dto.getIdCompra())
                .build();

        Preference preference = mock(Preference.class);
        when(preference.getId()).thenReturn("pref123");
        when(preference.getInitPoint()).thenReturn("https://www.mercadopago.cl/pagar");

        when(preferenceFactory.createFromDTO(dto)).thenReturn(preferenceRequest);
        when(preferenceClient.create(preferenceRequest)).thenReturn(preference);

        // When
        String resultado = mercadoPagoService.crearPreferencia(dto);

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.contains("https://www.mercadopago.cl/pagar"));

        verify(preferenceFactory).createFromDTO(dto);
        verify(preferenceClient).create(preferenceRequest);
    }
}
