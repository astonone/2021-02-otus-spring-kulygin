package ru.otus.kulygin.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.service.LocaleService;

@Aspect
@Component
public class TimerAspect {

    private final LocaleService localeService;

    public TimerAspect(LocaleService localeService) {
        this.localeService = localeService;
    }

    @Around("@annotation(ru.otus.kulygin.annotation.Measurable)")
    public Object logTimeOfTesting(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTestTime = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        System.out.println(localeService.getLocalizedString("testing.timer",
                String.valueOf((double) ((System.currentTimeMillis() - startTestTime) / 1000)))
        + localeService.getLocalizedString("testing.timer.sec"));

        return proceed;
    }

}
