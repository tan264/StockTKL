package com.example.stocktkl.service.impl;

import com.example.stocktkl.model.Order;
import com.example.stocktkl.model.Portfolio;
import com.example.stocktkl.model.Stock;
import com.example.stocktkl.model.User;
import com.example.stocktkl.repository.OrderRepository;
import com.example.stocktkl.repository.UserRepository;
import com.example.stocktkl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
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
    public List<Stock> getOwnedStocksForCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Can't find user"));

        return userRepository.findOwnedStocksByUser(currentUser);
    }

    @Override
    public List<Order> getOrdersForCurrentUser() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Cant find user"));

        return orderRepository.findByUser(currentUser);
    }
}
