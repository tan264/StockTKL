package com.example.stocktkl.controller;

import com.example.stocktkl.payload.request.LoginRequest;
import com.example.stocktkl.payload.request.SignupRequest;
import com.example.stocktkl.payload.response.CustomErrorResponse;
import com.example.stocktkl.payload.response.JwtResponse;
import com.example.stocktkl.service.impl.AuthServiceImpl;
import com.example.stocktkl.service.impl.StockServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthServiceImpl authServiceImpl;


    public AuthController(AuthServiceImpl authServiceImpl ) {
        this.authServiceImpl = authServiceImpl;

    }
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(
            @Valid @RequestBody LoginRequest loginRequest,
            BindingResult bindingResult

    ) {
        if (bindingResult.hasErrors()) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors()
                            .stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining(", ")));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return ResponseEntity.ok(authServiceImpl.authenticate(loginRequest));
    }
    @PostMapping("/signup")

    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signupRequest,
                                      BindingResult bindingResult

    ) {
        if (bindingResult.hasErrors()) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST,
                    bindingResult.getAllErrors()
                            .stream()
                            .map(ObjectError::getDefaultMessage)
                            .collect(Collectors.joining(", ")));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return ResponseEntity.ok(authServiceImpl.register(signupRequest));
    }

}
