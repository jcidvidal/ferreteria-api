package com.ferreteriapfeifer.ferreteria_api.repository;



import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

/**
 * Repositorio que maneja el acceso a datos de la entidad Cliente.
 * Extiende CrudRepository para operaciones básicas CRUD.
 */
@Repository
public class ClienteRepository {
    private static final String COLLECTION_NAME = "cliente";

    public void register(Cliente cliente) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME)
                .document(cliente.getIdCliente())
                .set(cliente);
        future.get();
    }

    public Cliente get(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        DocumentSnapshot snapshot = docRef.get().get();

        if (snapshot.exists()) {
            return snapshot.toObject(Cliente.class);
        } else {
            return null;
        }
    }
}
