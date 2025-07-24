package com.clutchcoders.dao;

import com.clutchcoders.Model.BachelorDashModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FetchBachelorProfiles {

    public static List<BachelorDashModel> getBachelorProfiles() {
        List<BachelorDashModel> profiles = new ArrayList<>();

        try {
            Firestore db = InitializeFirebase.db;
            CollectionReference bachelorsRef = db.collection("Bachelor");

            ApiFuture<QuerySnapshot> query = bachelorsRef.get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                Map<String, Object> data = doc.getData();

                BachelorDashModel model = new BachelorDashModel(
                    (String) data.get("name"),
                    (String) data.get("email"),
                    (String) data.get("contact"),
                    (String) data.get("gender"),
                    (String) data.get("propertyType"),
                    (String) data.get("state"),
                    (String) data.get("district"),
                    (String) data.get("city"),
                    (String) data.get("address"),
                    (String) data.get("pincode"),
                    (String) data.get("mapLink"),
                    (String) data.get("monthlyRent"),
                    (String) data.get("depositAmount"),
                    (String) data.get("maintenanceFee"),
                    (List<String>) data.get("amenities"),
                    (List<String>) data.get("features"),
                    (List<String>) data.get("preferences"),
                    (List<String>) data.get("photoUrls")
                );

                profiles.add(model);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return profiles;
    }


    public static List<BachelorDashModel> getAllBachelors() {
        List<BachelorDashModel> bachelors = new ArrayList<>();

        try {
            Firestore db = InitializeFirebase.db;
            CollectionReference bachelorRef = db.collection("bachelors"); // change collection name as needed

            bachelorRef.limit(5).get();


            ApiFuture<QuerySnapshot> future = bachelorRef.get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (DocumentSnapshot doc : documents) {
                BachelorDashModel model = doc.toObject(BachelorDashModel.class);
                bachelors.add(model);
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return bachelors;
    }

    // FetchBachelorProfiles.java
public static List<BachelorDashModel> getBachelorsByCity(String city) {
    List<BachelorDashModel> bachelors = new ArrayList<>();
    try {
        QuerySnapshot snapshot = InitializeFirebase.db.collection("Bachelor")
            .whereEqualTo("city", city)
            .get()
            .get();  // blocking call

        for (DocumentSnapshot doc : snapshot.getDocuments()) {
            BachelorDashModel model = doc.toObject(BachelorDashModel.class);
            if (model != null) {
                bachelors.add(model);
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return bachelors;
}

}
