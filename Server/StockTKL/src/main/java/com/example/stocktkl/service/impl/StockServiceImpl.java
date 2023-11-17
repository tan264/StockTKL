package com.example.stocktkl.service.impl;

import com.example.stocktkl.repository.StockRepository;
import com.example.stocktkl.service.StockService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;

@Service
public class StockServiceImpl implements StockService {
    private final StockRepository stockRepository;
    private final SimpMessagingTemplate template;
    private final ObjectMapper objectMapper;

    Logger log = LoggerFactory.getLogger(StockServiceImpl.class);
    public StockServiceImpl(StockRepository stockRepository,
                            SimpMessagingTemplate template,
                            ObjectMapper objectMapper) {

       this.stockRepository = stockRepository;
        this.template = template;
        this.objectMapper = objectMapper;
    }
    @Scheduled(fixedRate = 5000)
    public void updateRealTimeStock() throws JsonProcessingException {
        List<Object[]> realTimeStock = getRealTimeQuote();
        log.info("stocks : {}", realTimeStock);
        String jsonMessage = objectMapper.writeValueAsString(realTimeStock);
        template.convertAndSend("/topic/realTime", jsonMessage);
    }

    public List<Object[]> getRealTimeQuote() {
        return stockRepository.getStockWithLatestQuote();
    }
}
