package com.example.reactiveapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class ReactiveAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveAppApplication.class, args);
    }

}
