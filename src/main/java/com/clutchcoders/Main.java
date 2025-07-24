package com.clutchcoders;

import javax.security.auth.login.LoginContext;

import com.clutchcoders.dao.InitializeFirebase;

import javafx.application.Application;
import view.AdminLogInPage;
import view.ChatBox;
import view.LoginPage;
import view.Roommate;



public class Main {
    public static void main(String[] args) {
        InitializeFirebase initializeFirebase = new InitializeFirebase();
        initializeFirebase.setUpFirebase();
        System.out.println("Hello world!");
        Application.launch(LoginPage.class,args);
    }
}