package com.ferreteriapfeifer.ferreteria_api.repository;

import com.ferreteriapfeifer.ferreteria_api.model.Admin;
import com.ferreteriapfeifer.ferreteria_api.model.Login;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.gson.Gson;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class LoginAdminRepository {


    public Admin loginTest(Login dataAdmin) throws Exception {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("admin")
                .whereEqualTo("email", dataAdmin.getEmail())
                .get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        if (documents.isEmpty()) {
            throw new Exception("Admin no encontrado");
        }

        DocumentSnapshot snapshot = documents.get(0);

        Admin admin = new Admin();
        admin.setIdAdmin(snapshot.getId());
        admin.setNombre(snapshot.getString("nombre"));
        admin.setEmail(snapshot.getString("email"));
        admin.setTelefono(snapshot.getString("telefono"));
        admin.setContrasena(snapshot.getString("contrasena"));
        admin.setRol(snapshot.getString("rol"));
        return admin;
    }
}
