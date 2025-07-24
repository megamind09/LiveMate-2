package com.clutchcoders.Model;

import java.util.List;

public class UserProfileModel {
    public String name;
    public String email;
    public String contact;
    public String gender;
    public String occupationOrPropertyType;  // Works for both Bachelor (occupation) & Owner (propertyType)
    public String state;
    public String district;
    public String city;
    public String address;
    public String pincode;
    public String mapLink;
    public String monthlyRent;
    public String depositAmount;
    public String maintenanceFee;
    public List<String> amenities;
    public List<String> features;
    public List<String> preferences;
    public List<String> photoUrls;
    public boolean isOwner; // true = Owner, false = Bachelor

    public UserProfileModel() {}

    public UserProfileModel(String name, String email, String contact, String gender,
                            String occupationOrPropertyType, String state, String district,
                            String city, String address, String pincode, String mapLink,
                            String monthlyRent, String depositAmount, String maintenanceFee,
                            List<String> amenities, List<String> features, List<String> preferences,
                            List<String> photoUrls, boolean isOwner) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.gender = gender;
        this.occupationOrPropertyType = occupationOrPropertyType;
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
        this.isOwner = isOwner;
    }
}
