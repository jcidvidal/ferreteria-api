package com.ferreteriapfeifer.ferreteria_api.repository;

import com.ferreteriapfeifer.ferreteria_api.model.Boleta;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class BoletaRepository {

    private static final String COLLECTION_NAME = "boleta";

    public void registrarBoleta(Boleta boleta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME)
                .document(boleta.getIdBoleta())
                .set(boleta);
        future.get();
    }

    public Boleta obtenerIdBoleta(String idBoleta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(idBoleta);
        DocumentSnapshot snapshot = docRef.get().get();
        return snapshot.exists() ? snapshot.toObject(Boleta.class) : null;
    }

    public List<Boleta> obtenerBoletas() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Boleta> boletas = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            boletas.add(doc.toObject(Boleta.class));
        }
        return boletas;
    }

    public void eliminarBoleta(String idBoleta) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME).document(idBoleta).delete();
        future.get();
    }
}
