package com.clutchcoders.dao;

import com.clutchcoders.Model.BachelorDashModel;
import com.clutchcoders.Model.OwnerDashModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

public class ProfileDao {

    public static BachelorDashModel getBachelorProfile(String email) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<DocumentSnapshot> future = db.collection("Bachelor").document(email).get();
            DocumentSnapshot doc = future.get();
            if (doc.exists()) {
                return doc.toObject(BachelorDashModel.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static OwnerDashModel getOwnerProfile(String email) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            ApiFuture<DocumentSnapshot> future = db.collection("Owners").document(email).get();
            DocumentSnapshot doc = future.get();
            if (doc.exists()) {
                return doc.toObject(OwnerDashModel.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
