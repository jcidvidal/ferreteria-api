package com.ferreteriapfeifer.ferreteria_api.repository;

import com.ferreteriapfeifer.ferreteria_api.model.Compra;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class CompraRepository {

    private static final String COLLECTION_NAME = "compra";

    public void registrarCompra(Compra compra) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME)
                .document(compra.getIdCompra())
                .set(compra);
        future.get();
    }

    public Compra obtenerIdCompra(String idCompra) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(idCompra);
        DocumentSnapshot snapshot = docRef.get().get();
        return snapshot.exists() ? snapshot.toObject(Compra.class) : null;
    }

    public List<Compra> obtenerCompras() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Compra> compras = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            compras.add(doc.toObject(Compra.class));
        }
        return compras;
    }

    public void eliminarCompra(String idCompra) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME).document(idCompra).delete();
        future.get();
    }
}
