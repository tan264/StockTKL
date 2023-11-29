package com.example.stocktkl.controller;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.model.Stock;
import com.example.stocktkl.model.enum_class.StatusCode;
import com.example.stocktkl.payload.response.MessageResponse;
import com.example.stocktkl.payload.response.OrderResponse;
import com.example.stocktkl.payload.response.OwnedStockResponse;
import com.example.stocktkl.payload.response.WatchListResponse;
import com.example.stocktkl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getWatchlistedStocksForCurrentUser() {
        try {
            List<Stock> watchlist = userService.getWatchlistedStocksForCurrentUser();
            if (watchlist.isEmpty()) {
                return ResponseEntity.status(StatusCode.NOT_FOUND.code).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(watchlist);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Error retrieving watchlisted stocks", null));
        }
    }

    @GetMapping("/owned-stocks")
    public ResponseEntity<?> getOwnedStocksForCurrentUser() {
        try {
            List<OwnedStockResponse> ownedStocks = userService.getOwnedStocksForCurrentUser();
            if (ownedStocks.isEmpty()) {
                return ResponseEntity.status(StatusCode.NOT_FOUND.code).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(ownedStocks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Error retrieving owned stocks", null));
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<MessageResponse> getOrdersForCurrentUser() {
        try {
            List<Order> orders = userService.getOrdersForCurrentUser();
            return ResponseEntity.ok(
                    new MessageResponse(HttpStatus.OK.value(),
                            "Get orders successfully",
                            orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Error retrieving user orders", null));
        }
    }

    @PostMapping("/watchlist/add")
    public ResponseEntity<?> addStockToWatchlist(@RequestParam String symbol) {
        try {
            if (userService.addStockToWatchlist(symbol)) {
                return ResponseEntity.ok(
                        new WatchListResponse(HttpStatus.OK.value(),
                                "Adding stock successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new WatchListResponse(HttpStatus.NOT_FOUND.value(),
                                "Stock not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new WatchListResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Error adding stock to watchlist"));
        }
    }

    @DeleteMapping("/watchlist/remove")
    public ResponseEntity<?> removeStockFromWatchlist(@RequestParam String symbol) {
        try {
            if (userService.removeStockFromWatchlist(symbol)) {
                return ResponseEntity.ok(
                        new WatchListResponse(HttpStatus.OK.value(),
                                "Remove stock successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new WatchListResponse(HttpStatus.NOT_FOUND.value(),
                                "Stock not found in watchlist"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new WatchListResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Error removing stock from watchlist"));
        }
    }
}

