package com.clutchcoders.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import java.util.*;
import java.util.concurrent.ExecutionException;


public class AddBachelorDash {

    public static Firestore db = InitializeFirebase.db;

    // Save/Update Bachelor Data
    public static boolean putData(Map<String, Object> data, String email) {
        try {
            DocumentReference doc = db.collection("Bachelor").document(email);
            doc.set(data, SetOptions.merge());
            System.out.println("Bachelor's data added successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Failed to add Bachelor's data");
            e.printStackTrace();
            return false;
        }
    }

    // Fetch All Bachelors
    public static List<Map<String, Object>> getAllData() {
        List<Map<String, Object>> bachelors = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> future = db.collection("Bachelor").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot doc : documents) {
                Map<String, Object> data = doc.getData();
                data.put("email", doc.getId()); // Ensure email is included
                bachelors.add(data);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return bachelors;
    }

    // Delete Bachelor by Email
    public static void deleteData(String email) {
        try {
            db.collection("Bachelor").document(email).delete();
            System.out.println("Bachelor deleted successfully: " + email);
        } catch (Exception e) {
            System.out.println("Failed to delete bachelor: " + email);
            e.printStackTrace();
        }
    }
}
