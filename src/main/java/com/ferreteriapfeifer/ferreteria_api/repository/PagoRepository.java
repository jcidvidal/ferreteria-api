package com.ferreteriapfeifer.ferreteria_api.repository;

import com.ferreteriapfeifer.ferreteria_api.model.Pago;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

@Repository
public class PagoRepository {

    private static final String COLLECTION_NAME = "pagos";

    public String guardarPago(Pago pago) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> result = db.collection(COLLECTION_NAME)
                .document(String.valueOf(pago.getPaymentId()))
                .set(pago);
        return result.get().getUpdateTime().toString();
    }
}
