package com.ferreteriapfeifer.ferreteria_api.controller;

import com.ferreteriapfeifer.ferreteria_api.dto.PaymentNotificationDTO;
import com.ferreteriapfeifer.ferreteria_api.dto.PreferenceRequestDTO;
import com.ferreteriapfeifer.ferreteria_api.model.Pago;
import com.ferreteriapfeifer.ferreteria_api.repository.PagoRepository;
import com.ferreteriapfeifer.ferreteria_api.service.MercadoPagoService;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pago")
public class MercadoPagoController {

    private final MercadoPagoService mercadoPagoService;
    private final PagoRepository pagoRepository;

    public MercadoPagoController(MercadoPagoService mercadoPagoService,
                                 PagoRepository pagoRepository) {
        this.mercadoPagoService = mercadoPagoService;
        this.pagoRepository = pagoRepository;
    }


    @Operation(
            summary = "Crear preferencia de pago (MercadoPago)",
            description = "Genera un link de pago para un producto usando MercadoPago"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Preferencia creada con éxito"),
            @ApiResponse(responseCode = "500", description = "Error interno al crear preferencia")
    })
    @PostMapping("/crear-preferencia")
    public ResponseEntity<String> crearPreferencia(@RequestBody PreferenceRequestDTO request) {
        try {
            String initPoint = mercadoPagoService.crearPreferencia(request);
            return ResponseEntity.ok(initPoint);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear preferencia: " + e.getMessage());
        }
    }


    @Operation(
            summary = "Webhook de MercadoPago",
            description = "Recibe notificaciones automáticas de pagos. No requiere token. Solo procesa topic = 'payment'."
    )
    @ApiResponse(responseCode = "200", description = "Webhook procesado correctamente")
    @PostMapping("/webhook")
    public ResponseEntity<String> recibirWebhook(@RequestBody PaymentNotificationDTO notificacion) {
        Long paymentId = notificacion.getId();
        String topic = notificacion.getTopic();

        System.out.println("✅ Webhook recibido. Topic: " + topic + ", ID: " + paymentId);

        if (paymentId == null || topic == null) {
            return ResponseEntity.badRequest().body("Faltan campos requeridos en el webhook.");
        }

        if (!"payment".equalsIgnoreCase(topic)) {
            return ResponseEntity.ok("Evento ignorado (no es de tipo 'payment')");
        }

        try {
            PaymentClient client = new PaymentClient();
            Payment payment = client.get(paymentId);

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

            return ResponseEntity.ok("Pago procesado correctamente");

        } catch (Exception e) {
            System.out.println("Error guardando estado del pago:");
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno procesando el webhook.");
        }
    }

}
