package com.ferreteriapfeifer.ferreteria_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pago {
    private Long paymentId;
    private String status;
    private String externalReference;
    private String preferenceId;
    private String paymentTypeId;
    private String methodId;
}
