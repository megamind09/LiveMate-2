package com.clutchcoders.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;







// public class SignUpModel {
//     String fullName;
//     String  email;
//     String password;

//     public SignUpModel(String fullName,String email,String password){
//         this.fullName=fullName;
//         this.email=email;
//         this.password=password;
//     }

//     public Map<String,String> getMap(){
//         Map<String,String> data = new HashMap<String,String>();
//         data.put("fullname", fullName);
//         data.put("email", email);
//         data.put("password", password);

//         return data;
//     }

// }



public class OwnerDashModel {
    public static final String ownerDash = null;

    // Basic Info
    public String name;
    public String email;
    public String contact;
    public String gender;
    public String propertyType;

    // Location Info
    public String state;
    public String district;
    public String city;
    public String address;
    public String pincode;
    public String mapLink;

    // Rent Details
    public String monthlyRent;
    public String depositAmount;
    public String maintenanceFee;

    // Property Details
    public List<String> amenities;
    public List<String> features;
    public List<String> preferences;

    // Optional: Uploaded photos
    public List<String> photoUrls;

    public OwnerDashModel() {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.gender = gender;
        this.propertyType = propertyType;
        this.state = state;
        this.district = district;
        this.city = city;
        this.address = address;
        this.pincode = pincode;
        this.mapLink = mapLink;
        this.monthlyRent = monthlyRent;
        this.depositAmount = depositAmount;
        this.maintenanceFee = maintenanceFee;
        this.amenities = amenities;
        this.features = features;
        this.preferences = preferences;
        this.photoUrls = photoUrls;
    }



    public List<String> getProfileImageUrl() {
    return photoUrls;
}

public String getName() {
    return name;
}

public String getAddress() {
    return address;
}

public String getRent() {
    return monthlyRent;
}

public String getCity() {
    return city;
}

   public Map<String, Object> getMap() {
    Map<String, Object> data = new HashMap<>();

    data.put("name", name);
    data.put("email", email);
    data.put("contact", contact);
    data.put("gender", gender);
    data.put("propertyType", propertyType);

    data.put("state", state);
    data.put("district", district);
    data.put("city", city);
    data.put("address", address);
    data.put("pincode", pincode);
    data.put("mapLink", mapLink);

    data.put("monthlyRent", monthlyRent);
    data.put("depositAmount", depositAmount);
    data.put("maintenanceFee", maintenanceFee);

    // Store lists as-is (Firestore supports arrays)
    data.put("amenities", amenities);
    data.put("features", features);
    data.put("preferences", preferences);
    data.put("photoUrls", photoUrls);

    return data;
}

}
