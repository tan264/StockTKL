package com.example.stocktkl.controller;

import com.example.stocktkl.payload.request.LoginRequest;
import com.example.stocktkl.payload.request.SignupRequest;
import com.example.stocktkl.payload.response.JwtResponse;
import com.example.stocktkl.payload.response.MessageResponse;
import com.example.stocktkl.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthServiceImpl authServiceImpl;
    public AuthController(AuthServiceImpl authServiceImpl) {
        this.authServiceImpl = authServiceImpl;
    }
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authServiceImpl.authenticate(loginRequest));
    }
    @PostMapping("/signup")

    public ResponseEntity<JwtResponse> register(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(authServiceImpl.register(signupRequest));
    }

}
