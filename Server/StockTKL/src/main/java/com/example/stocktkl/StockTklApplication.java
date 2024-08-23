package com.example.stocktkl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StockTklApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockTklApplication.class, args);
    }

}
