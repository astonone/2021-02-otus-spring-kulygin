package ru.otus.kulygin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.kulygin.service.RunnerService;

@SpringBootApplication
public class HT03SpringBoot {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HT03SpringBoot.class, args);

        RunnerService runnerService = context.getBean(RunnerService.class);
        runnerService.run();
    }
}
