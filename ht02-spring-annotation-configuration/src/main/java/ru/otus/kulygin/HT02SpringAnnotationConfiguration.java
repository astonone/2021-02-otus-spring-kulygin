package ru.otus.kulygin;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.kulygin.service.RunnerService;

@ComponentScan
@PropertySource(value = "classpath:application.properties")
public class HT02SpringAnnotationConfiguration {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(HT02SpringAnnotationConfiguration.class);

        RunnerService runner = applicationContext.getBean(RunnerService.class);
        runner.run();
    }
}
