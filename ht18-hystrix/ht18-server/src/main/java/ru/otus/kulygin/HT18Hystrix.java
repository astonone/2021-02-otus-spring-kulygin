package ru.otus.kulygin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
public class HT18Hystrix {
    public static void main(String[] args) {
        SpringApplication.run(HT18Hystrix.class, args);
    }
}
