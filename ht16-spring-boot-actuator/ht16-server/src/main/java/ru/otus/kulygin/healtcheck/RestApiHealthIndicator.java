package ru.otus.kulygin.healtcheck;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.otus.kulygin.dto.HealthStatusDto;

import java.util.Objects;

@Component
public class RestApiHealthIndicator implements HealthIndicator {

    private final String restApiHealthCheckUrl;
    private final RestTemplate restTemplate;

    public RestApiHealthIndicator(@Value("${app.health-check.url}") String restApiHealthCheckUrl,
                                  RestTemplate restTemplate) {
        this.restApiHealthCheckUrl = restApiHealthCheckUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public Health health() {
        try {
            ResponseEntity<HealthStatusDto> responseEntity = restTemplate.getForEntity(restApiHealthCheckUrl, HealthStatusDto.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return Health.up()
                        .withDetail("rest-api-status",
                                Objects.requireNonNull(responseEntity.getBody()).getStatus())
                        .build();
            } else {
                return Health.down().build();
            }
        } catch (Exception e) {
            return Health.down().withException(e).build();
        }
    }

}