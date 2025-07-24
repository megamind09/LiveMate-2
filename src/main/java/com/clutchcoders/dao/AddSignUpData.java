package com.clutchcoders.dao;

import java.util.Map;

import com.google.cloud.firestore.DocumentReference;

public class AddSignUpData {
    
    public static void putData(Map<String,String> data,String email){
        try{

           DocumentReference doc = InitializeFirebase.db.collection("users").document(email);
           doc.set(data);
            System.out.println("DATA ADDED SUCCESFULLY...!");
            
        }catch(Exception e){
            System.out.println("FAILED TO ADD DATA ...!");
        }
    }
}
