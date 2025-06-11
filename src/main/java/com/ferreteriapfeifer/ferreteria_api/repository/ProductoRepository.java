package com.ferreteriapfeifer.ferreteria_api.repository;


import com.ferreteriapfeifer.ferreteria_api.model.Producto;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Repository
public class ProductoRepository {

    private static final String COLLECTION_NAME = "producto";

    public void registrarProducto(Producto producto) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME)
                .document(producto.getIdProducto())
                .set(producto);
        future.get();
    }

    public Producto obtenerIdProducto(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        DocumentSnapshot snapshot = docRef.get().get();

        return snapshot.exists() ? snapshot.toObject(Producto.class) : null;
    }

    public List<Producto> obtenerProductos() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Producto> productos = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            productos.add(doc.toObject(Producto.class));
        }
        return productos;
    }

    public void eliminarProducto(String idProducto) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME).document(idProducto).delete();
        future.get();
    }
}