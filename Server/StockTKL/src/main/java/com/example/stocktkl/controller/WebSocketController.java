package com.example.stocktkl.controller;

import com.example.stocktkl.service.impl.StockServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class WebSocketController {

    private final StockServiceImpl stockServiceImpl;


    public WebSocketController(StockServiceImpl stockServiceImpl)
    {

        this.stockServiceImpl = stockServiceImpl;
    }
    @MessageMapping("/realTime")
    @SendTo("/topic/public")
    public void sendMessage() throws JsonProcessingException {
       stockServiceImpl.updateRealTimeStock();
    }
}
