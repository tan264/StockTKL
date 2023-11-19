package com.example.stocktkl.service.impl;

import com.example.stocktkl.model.RealtimeQuote;
import com.example.stocktkl.repository.RealtimeQuoteRepository;
import com.example.stocktkl.service.IRealtimeQuoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Tan Dang
 * @since 18/11/2023 - 9:56 am
 */
@Service
@Log
public class RealtimeQuoteService implements IRealtimeQuoteService {

    private final RealtimeQuoteRepository realtimeQuoteRepository;
    private final SimpMessagingTemplate messagingTemplate;

    private final ObjectMapper objectMapper;

    public RealtimeQuoteService(RealtimeQuoteRepository realtimeQuoteRepository,
                                SimpMessagingTemplate messagingTemplate, ObjectMapper objectMapper) {

        this.realtimeQuoteRepository = realtimeQuoteRepository;
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 5000)
    public void getRealtimeQuote() {
        String topic = "/topic/muaxuandautien";
        List<RealtimeQuote> result = realtimeQuoteRepository.getRealtimeQuote();
        try {
            String jsonMessage = objectMapper.writeValueAsString(result);
            messagingTemplate.convertAndSend(topic, jsonMessage);
            log.info(result.toString());
        } catch (JsonProcessingException exception) {
            log.info(exception.getMessage());
        }
    }

    @Override
    public List<RealtimeQuote> getQuotes() {
        return realtimeQuoteRepository.getRealtimeQuote();
    }
}
