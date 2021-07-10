package ru.otus.kulygin.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.kulygin.dto.HealthStatusDto;

@RestController
public class HealthCheckController {

    @GetMapping("/api/health-check")
    public ResponseEntity<?> check() {
        return new ResponseEntity<>(HealthStatusDto.builder().status("Rest API works").build(), HttpStatus.OK);
    }

}
