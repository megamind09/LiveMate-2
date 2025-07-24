package com.clutchcoders.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class AddOwnerDash {

    public static Firestore db = InitializeFirebase.db;

    // Save/Update Owner Data
    public static void putData(Map<String, Object> data, String email) {
        try {
            DocumentReference doc = db.collection("Owners").document(email);
            doc.set(data, SetOptions.merge());
            System.out.println("Owner data added successfully");
        } catch (Exception e) {
            System.out.println("Failed to add Owner data");
            e.printStackTrace();
        }
    }

    // Fetch All Owners
    public static List<Map<String, Object>> getAllData() {
        List<Map<String, Object>> owners = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> future = db.collection("Owners").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                Map<String, Object> data = doc.getData();
                data.put("email", doc.getId()); // Ensure email is included
                owners.add(data);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return owners;
    }

    // Delete Owner by Email
    public static void deleteData(String email) {
        try {
            db.collection("Owners").document(email).delete();
            System.out.println("Owner deleted successfully: " + email);
        } catch (Exception e) {
            System.out.println("Failed to delete owner: " + email);
            e.printStackTrace();
        }
    }
}
