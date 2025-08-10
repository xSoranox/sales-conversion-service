package com.example.sales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SalesConversionApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalesConversionApplication.class, args);
    }
}