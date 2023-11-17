package com.example.stocktkl.service;

import com.example.stocktkl.payload.request.LoginRequest;
import com.example.stocktkl.payload.request.SignupRequest;
import com.example.stocktkl.payload.response.JwtResponse;


public interface AuthService {
    public JwtResponse authenticate(LoginRequest loginRequest);
    public JwtResponse register(SignupRequest signupRequest);

    class StockService {

    }
}
