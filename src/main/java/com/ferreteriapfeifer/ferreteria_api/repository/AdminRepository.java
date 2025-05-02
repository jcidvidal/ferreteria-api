package com.ferreteriapfeifer.ferreteria_api.repository;

import com.ferreteriapfeifer.ferreteria_api.model.Admin;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Repository
public class AdminRepository  {

    private static final String COLLECTION_NAME = "admin";

    public void register(Admin admin) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> future = db.collection(COLLECTION_NAME)
                .document(admin.getIdAdmin())
                .set(admin);
        future.get();
    }

    public Admin get(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(id);
        DocumentSnapshot snapshot = docRef.get().get();

        if (snapshot.exists()) {
            return snapshot.toObject(Admin.class);
        } else {
            return null;
        }
    }

    public List<Admin> getAll() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Admin> admins = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            Admin admin = doc.toObject(Admin.class);
            admins.add(admin);
        }
        return admins;
    }
    public void delete(String adminId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = db.collection(COLLECTION_NAME).document(adminId).delete();
        writeResult.get();

    }

}
