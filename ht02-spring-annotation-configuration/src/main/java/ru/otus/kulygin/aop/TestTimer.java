package ru.otus.kulygin.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Aspect
@Component
public class TestTimer {

    private final MessageSource messageSource;

    @Value("${app.locale}")
    private Locale locale;

    public TestTimer(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Around("execution(* ru.otus.kulygin.service.impl.TestingServiceImpl.*(..))")
    public Object logBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        final long startTestTime = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        System.out.println(messageSource.getMessage("testing.timer", null, locale)
                + (double) ((System.currentTimeMillis() - startTestTime) / 1000)
                + " " + messageSource.getMessage("testing.timer.sec", null, locale));

        return proceed;
    }
}
