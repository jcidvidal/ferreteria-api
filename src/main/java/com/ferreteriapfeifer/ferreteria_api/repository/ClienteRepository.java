package com.ferreteriapfeifer.ferreteria_api.repository;

import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class ClienteRepository {
    private static final String COLLECTION_NAME = "cliente";

    public void registrarCliente(Cliente cliente) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME)
                .document(cliente.getIdCliente())
                .set(cliente);
        future.get();
    }

    public Cliente obtenerIdCliente(String idCliente) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(idCliente);
        DocumentSnapshot snapshot = docRef.get().get();

        if (snapshot.exists()) {
            return snapshot.toObject(Cliente.class);
        } else {
            return null;
        }
    }

    public List<Cliente> obtenerClientes() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Cliente> clientes = new ArrayList<>();

        for (QueryDocumentSnapshot doc : documents) {
            try {
                Cliente cliente = doc.toObject(Cliente.class);
                if (cliente != null && cliente.getContrasena() != null) {
                    clientes.add(cliente);
                }
            } catch (Exception e) {
                System.err.println("Error al mapear documento a Cliente: " + e.getMessage());
            }
        }
        return clientes;
    }

    public void eliminarCliente(String idCliente) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = db.collection(COLLECTION_NAME).document(idCliente).delete();
        writeResult.get();
    }
}
