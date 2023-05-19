package ru.otus.kulygin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

@IntegrationComponentScan
@EnableIntegration
@SpringBootApplication
public class HT15SpringIntegration {
    public static void main(String[] args) {
        SpringApplication.run(HT15SpringIntegration.class, args);
    }
}
