package com.example.stocktkl.controller;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.model.Stock;
import com.example.stocktkl.payload.response.MessageResponse;
import com.example.stocktkl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/watchlist")
    public ResponseEntity<List<Stock>> getWatchlistedStocksForCurrentUser() {
        List<Stock> watchlist = userService.getWatchlistedStocksForCurrentUser();
        if (watchlist.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(watchlist);
    }

    @GetMapping("/owned-stocks")
    public ResponseEntity<List<Stock>> getOwnedStocksForCurrentUser() {
        List<Stock> ownedStocks = userService.getOwnedStocksForCurrentUser();
        if (ownedStocks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ownedStocks);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrdersForCurrentUser() {
        List<Order> orders = userService.getOrdersForCurrentUser();
        if (orders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orders);
    }
}
