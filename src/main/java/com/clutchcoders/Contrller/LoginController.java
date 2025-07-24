package com.clutchcoders.Contrller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

import com.clutchcoders.Model.UserProfileModel;
import com.clutchcoders.dao.SessionData; 





public class LoginController {

    // Sign In Wali Method
    private static final String API_KEY = "AIzaSyCatfFmvVmVBc52oXLQ52f25eohXtHvf08";

     // Add at the top
    
    public boolean signInWithEmailAndPassword(String email, String password) {
        String link = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY;
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
    
            String payload = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
                email, password
            );
    
            try (OutputStream os = connection.getOutputStream()) {
                os.write(payload.getBytes());
            }
    
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // Read response
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }
    
                // Parse JSON
                JSONObject json = new JSONObject(response.toString());
                String loggedInEmail = json.getString("email");
    
                // Save in SessionData
               SessionData.loggedInUser = new UserProfileModel(
    "User",               // name
    loggedInEmail,        // email
    "-",                  // contact
    "-",                  // gender
    "-",                  // occupation/propertyType
    "-",                  // state
    "-",                  // district
    "-",                  // city
    "-",                  // address
    "-",                  // pincode
    "-",                  // mapLink
    "-",                  // monthlyRent
    "-",                  // depositAmount
    "-",                  // maintenanceFee
    null,                 // amenities
    null,                 // features
    null,                 // preferences
    null,                 // photoUrls
    true                  // isOwner
);

                System.out.println("Login successful! Email: " + loggedInEmail);
                return true;
            } else {
                // Print error
                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                }
                return false;
            }
    
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    


    // SignUP Wala API Call

  

public boolean signUp(String email, String password) {
    String link = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=" + API_KEY;
    try {
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String payload = String.format(
            "{\"email\":\"%s\",\"password\":\"%s\",\"returnSecureToken\":true}",
            email, password
        );

        try (OutputStream os = connection.getOutputStream()) {
            os.write(payload.getBytes());
        }

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            // Read response
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
            }

            // Parse JSON
            JSONObject json = new JSONObject(response.toString());
            String registeredEmail = json.getString("email");

            // Save in SessionData
           SessionData.loggedInUser = new UserProfileModel(
    "User",               // name
    registeredEmail,        // email
    "-",                  // contact
    "-",                  // gender
    "-",                  // occupation/propertyType
    "-",                  // state
    "-",                  // district
    "-",                  // city
    "-",                  // address
    "-",                  // pincode
    "-",                  // mapLink
    "-",                  // monthlyRent
    "-",                  // depositAmount
    "-",                  // maintenanceFee
    null,                 // amenities
    null,                 // features
    null,                 // preferences
    null,                 // photoUrls
    true                  // isOwner
);

            System.out.println("Signup successful! Email: " + registeredEmail);
            return true;
        } else {
            // Print error
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
            return false;
        }

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

}
