package com.ferreteriapfeifer.ferreteria_api.repository;

import com.ferreteriapfeifer.ferreteria_api.model.Cliente;
import com.ferreteriapfeifer.ferreteria_api.model.Login;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LoginClienteRepository {

    public Cliente loginTest(Login dataCliente) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("cliente")
                .whereEqualTo("email", dataCliente.getEmail())
                .get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        if (documents.isEmpty()) {
            throw new Exception("Cliente no encontrado");
        }

        DocumentSnapshot snapshot = documents.get(0);

        Cliente cliente = new Cliente();
        cliente.setIdCliente(snapshot.getId());
        cliente.setNombre(snapshot.getString("nombre"));
        cliente.setEmail(snapshot.getString("email"));
        cliente.setTelefono(snapshot.getString("telefono"));
        cliente.setContrasena(snapshot.getString("contrasena"));
        cliente.setRol(snapshot.getString("rol"));

        return cliente;
    }
}
