package com.example.stocktkl.controller;

import com.example.stocktkl.payload.response.MessageResponse;
import com.example.stocktkl.service.IRealtimeQuoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tan Dang
 * @since 18/11/2023 - 11:07 pm
 */
@RestController
@RequestMapping("api/quote")
public class QuoteController {

    private final IRealtimeQuoteService realtimeQuoteService;

    public QuoteController(IRealtimeQuoteService realtimeQuoteService) {
        this.realtimeQuoteService = realtimeQuoteService;
    }

    @GetMapping("/get-realtime-quotes")
    public ResponseEntity<MessageResponse> getRealTimeQuotes() {
        try {
            return ResponseEntity.ok(
                    new MessageResponse(HttpStatus.OK.value(), "Get realtime quotes successfully",
                            realtimeQuoteService.getQuotes()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new MessageResponse(HttpStatus.OK.value(), "Get realtime quotes successfully",
                            realtimeQuoteService.getQuotes()));
        }
    }

}
