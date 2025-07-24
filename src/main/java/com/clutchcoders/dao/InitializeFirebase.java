package com.clutchcoders.dao;

import java.io.FileInputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class InitializeFirebase {
    public static Firestore db;

    public void setUpFirebase(){
        try{
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/livemate-8bfba-firebase-adminsdk-fbsvc-f5793ad242.json");

        FirebaseOptions options =  FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setStorageBucket("livemate-8bfba.firebasestorage.app")
        .build();

        FirebaseApp.initializeApp(options);

        db = FirestoreClient.getFirestore();
        System.out.println("Firebase Initialized Succesfully ....!");

    }catch(Exception e){

        System.out.println("Failed to Initialized Firebase ....!");
    }
}
}