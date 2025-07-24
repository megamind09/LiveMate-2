package com.clutchcoders.Model;

import java.util.HashMap;
import java.util.Map;

public class SignUpModel {
    String fullName;
    String  email;
    String password;

    public SignUpModel(String fullName,String email,String password){
        this.fullName=fullName;
        this.email=email;
        this.password=password;
    }

    public Map<String,String> getMap(){
        Map<String,String> data = new HashMap<String,String>();
        data.put("fullname", fullName);
        data.put("email", email);
        data.put("password", password);

        return data;
    }

}
