package com.clutchcoders.dao;

import com.clutchcoders.Model.OwnerDashModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FetchOwnerProfiles {

    public static List<OwnerDashModel> getOwnerProfiles() {
        List<OwnerDashModel> ownerList = new ArrayList<>();

        try {
            Firestore db = InitializeFirebase.db;
            ApiFuture<QuerySnapshot> query = db.collection("Owners").get();
            List<QueryDocumentSnapshot> documents = query.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                OwnerDashModel model = new OwnerDashModel();

                model.name = doc.getString("name");
                model.city = doc.getString("city");
                model.state = doc.getString("state");
                model.monthlyRent = doc.getString("monthlyRent");
                model.mapLink = doc.getString("mapLink");
                model.photoUrls = (List<String>) doc.get("photoUrls");

                ownerList.add(model);
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return ownerList;
    }



    //  public static List<OwnerDashModel> getAllOwners() {
    //     List<OwnerDashModel> owners = new ArrayList<>();

    //     try {
    //         Firestore db = InitializeFirebase.db;
    //         CollectionReference ownerRef = db.collection("owners"); // replace with your actual Firestore collection

    //         ownerRef.limit(5).get();  // fetch only 10 documents


    //         ApiFuture<QuerySnapshot> future = ownerRef.get();
    //         List<QueryDocumentSnapshot> documents = future.get().getDocuments();

    //         for (DocumentSnapshot doc : documents) {
    //             OwnerDashModel model = doc.toObject(OwnerDashModel.class);
    //             owners.add(model);
    //         }

    //     } catch (InterruptedException | ExecutionException e) {
    //         e.printStackTrace();
    //     }

    //     return owners;
    // }


    
    public static List<OwnerDashModel> getOwnersByCity(String city) {
    List<OwnerDashModel> ownerList = new ArrayList<>();

    try {
        Firestore db = InitializeFirebase.db;
        ApiFuture<QuerySnapshot> query = db.collection("Owners")
                .whereEqualTo("city", city)
                .get();
        List<QueryDocumentSnapshot> documents = query.get().getDocuments();

        for (QueryDocumentSnapshot doc : documents) {
            OwnerDashModel model = new OwnerDashModel();
            model.name = doc.getString("name");
            model.city = doc.getString("city");
            model.state = doc.getString("state");
            model.monthlyRent = doc.getString("monthlyRent");
            model.mapLink = doc.getString("mapLink");
            model.photoUrls = (List<String>) doc.get("photoUrls");
            ownerList.add(model);
        }

    } catch (InterruptedException | ExecutionException e) {
        e.printStackTrace();
    }

    return ownerList;
}

}

