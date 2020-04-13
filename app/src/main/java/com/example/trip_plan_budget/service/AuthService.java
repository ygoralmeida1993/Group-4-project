package com.example.trip_plan_budget.service;

import com.google.firebase.auth.FirebaseAuth;

public class AuthService {
    private static AuthService instance;
    private FirebaseAuth auth;

    private AuthService() {
        auth = FirebaseAuth.getInstance();
    }

    public static AuthService getInstance() {
        if (instance == null) instance = new AuthService();
        return instance;
    }

    public String getID() {
        return auth.getCurrentUser().getUid();
    }
}
