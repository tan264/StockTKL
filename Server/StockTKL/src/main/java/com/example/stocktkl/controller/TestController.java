package com.example.stocktkl.controller;

import com.example.stocktkl.payload.request.LoginRequest;
import com.example.stocktkl.payload.request.SignupRequest;
import com.example.stocktkl.payload.response.JwtResponse;
import com.example.stocktkl.service.impl.AuthServiceImpl;
import com.example.stocktkl.service.impl.StockServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {

    private final StockServiceImpl stockService;
    private final ObjectMapper objectMapper;

    public TestController( StockServiceImpl stockService, ObjectMapper objectMapper) {
        this.stockService = stockService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/realTime")

    public ResponseEntity<?> realTime() throws JsonProcessingException {
        List<Object[]> realTimeQuotes = stockService.getRealTimeQuote();

        String jsonMessage = objectMapper.writeValueAsString(realTimeQuotes);
        return ResponseEntity.ok(jsonMessage);
    }

}
