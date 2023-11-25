package com.example.stocktkl.controller;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.model.enum_class.EOrderDirection;
import com.example.stocktkl.model.enum_class.EOrderType;
import com.example.stocktkl.payload.request.OrderRequest;
import com.example.stocktkl.payload.response.MessageResponse;
import com.example.stocktkl.service.IOrderService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
@Log
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/send-order")
    public ResponseEntity<MessageResponse> sendOrder(@RequestBody OrderRequest orderRequest) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            orderRequest.setUserId(authentication.getName());
//        }
        // Need to get userId from authentication
        Long stockId;
        try {
            stockId = orderService.getStockIdBySymbol(orderRequest.getStockSymbol());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse(HttpStatus.BAD_REQUEST.value(),
                            "Stock symbol is invalid",
                            orderRequest));
        }
        if (orderRequest.getDirection() == EOrderDirection.SELL && !orderService.canExecuteSellRequest(
                stockId, orderRequest.getQuantity())) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse(HttpStatus.BAD_REQUEST.value(),
                            "You don't have enough stock to sell",
                            orderRequest));
        }
        Order order = new Order().toBuilder()
                .stockId(stockId)
                .userId(orderRequest.getUserId())
                .orderType(orderRequest.getOrderType())
                .direction(orderRequest.getDirection())
                .quantity(orderRequest.getQuantity())
                .build();
        if (order.getOrderType() == EOrderType.LIMIT) {
            order.setPrice(BigDecimal.valueOf(orderRequest.getPrice()));
        }
        try {
            boolean result = orderService.sendToJadeRabbit(order);
            if (result) {
                return ResponseEntity.ok(
                        new MessageResponse(HttpStatus.OK.value(),
                                "Send order successfully",
                                order));
            } else {
                return ResponseEntity.internalServerError().body(
                        new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                "Send order unsuccessfully",
                                null));
            }
        } catch (Exception e) {
            log.severe(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Send order unsuccessfully",
                            e.getMessage()));
        }
    }
}
