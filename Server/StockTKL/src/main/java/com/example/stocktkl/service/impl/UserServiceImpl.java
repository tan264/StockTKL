package com.example.stocktkl.service.impl;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.model.Stock;
import com.example.stocktkl.model.User;
import com.example.stocktkl.payload.response.OrderResponse;
import com.example.stocktkl.payload.response.OwnedStockResponse;
import com.example.stocktkl.repository.OrderRepository;
import com.example.stocktkl.repository.StockRepository;
import com.example.stocktkl.repository.UserRepository;
import com.example.stocktkl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    private final StockRepository stockRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository, StockRepository stockRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public List<Stock> getWatchlistedStocksForCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Cant find user"));

        Set<Stock> watchlistedStocks = currentUser.getWatchlistedStocks();
        return new ArrayList<>(watchlistedStocks);
    }

    @Override
    public List<OwnedStockResponse> getOwnedStocksForCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Can't find user"));

        return userRepository.findOwnedStocksByUser(currentUser);
    }

    @Override
    public List<OrderResponse> getOrdersForCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Cant find user"));

        return orderRepository.findByUser(currentUser);
    }

    @Override
    public boolean addStockToWatchlist(String symbol) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Cant find user"));

        Optional<Stock> stockToAdd = stockRepository.findBySymbol(symbol);
        if (stockToAdd.isPresent()) {
            currentUser.getWatchlistedStocks().add(stockToAdd.get());
            userRepository.save(currentUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeStockFromWatchlist(String symbol) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Cant find user"));

        Optional<Stock> stockToRemove = stockRepository.findBySymbol(symbol);
        if (stockToRemove.isPresent()) {
            currentUser.getWatchlistedStocks().remove(stockToRemove.get());
            userRepository.save(currentUser);
            return true;
        }
        return false;
    }
}
