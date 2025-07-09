package com.ferreteriapfeifer.ferreteria_api.service;

import com.ferreteriapfeifer.ferreteria_api.Factory.PreferenceFactory;
import com.ferreteriapfeifer.ferreteria_api.dto.PreferenceRequestDTO;
import com.ferreteriapfeifer.ferreteria_api.model.Compra;
import com.ferreteriapfeifer.ferreteria_api.model.Pago;
import com.ferreteriapfeifer.ferreteria_api.repository.PagoRepository;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class MercadoPagoServiceImpl implements MercadoPagoService {

    private final PagoRepository pagoRepository;
    private final CompraService compraService;
    private final PreferenceFactory preferenceFactory;
    private final PreferenceClient preferenceClient;
    private final PaymentClient paymentClient;

    @Autowired
    public MercadoPagoServiceImpl(PagoRepository pagoRepository,
                                  CompraService compraService,
                                  PreferenceFactory preferenceFactory,
                                  PreferenceClient preferenceClient,
                                  PaymentClient paymentClient) {
        this.pagoRepository = pagoRepository;
        this.compraService = compraService;
        this.preferenceFactory = preferenceFactory;
        this.preferenceClient = preferenceClient;
        this.paymentClient = paymentClient;
    }

    @Override
    public String procesarWebhook(String paymentId) throws Exception {
        try {
            Long idNumerico = Long.parseLong(paymentId);
            Payment payment = paymentClient.get(idNumerico);

            Pago pago = new Pago(
                    payment.getId(),
                    payment.getStatus(),
                    payment.getExternalReference(),
                    payment.getExternalReference(),
                    payment.getPaymentTypeId(),
                    payment.getPaymentMethodId()
            );

            pagoRepository.guardarPago(pago);
            System.out.println("Estado del pago guardado en Firestore: " + pago.getStatus());

            actualizarEstadoCompra(payment);

            return "Pago procesado correctamente";
        } catch (NumberFormatException ex) {
            System.out.println(" ID recibido no es numérico: " + paymentId);
            throw new IllegalArgumentException("El ID de pago recibido no es un número válido.");
        }
    }

    private void actualizarEstadoCompra(Payment payment) throws ExecutionException, InterruptedException {
        String idCompra = payment.getExternalReference();
        Compra compra = compraService.obtenerIdCompra(idCompra);

        if (compra != null) {
            compra.setEstadoPago(payment.getStatus());
            compraService.registrarCompra(compra);
            System.out.println("Estado de la compra actualizado a: " + payment.getStatus());
        } else {
            System.out.println("⚠ No se encontró una compra con ID: " + idCompra);
        }
    }

    @Override
    public String crearPreferencia(PreferenceRequestDTO request) throws Exception {
        try {
            PreferenceRequest preferenceRequest = preferenceFactory.createFromDTO(request);
            Preference preference = preferenceClient.create(preferenceRequest);

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
