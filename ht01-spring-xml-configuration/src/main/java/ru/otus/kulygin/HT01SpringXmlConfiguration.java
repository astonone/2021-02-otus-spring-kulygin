package ru.otus.kulygin;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.kulygin.service.RunnerService;

public class HT01SpringXmlConfiguration {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("app-context.xml");

        RunnerService runner = applicationContext.getBean(RunnerService.class);
        runner.run();
    }
}
