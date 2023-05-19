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

    @Around("execution(* ru.otus.kulygin.service.impl.TestingServiceImpl.doTest(*))")
    public Object logTimeOfTesting(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTestTime;

        try {
            startTestTime = System.currentTimeMillis();
        } catch (Exception e) {
            startTestTime = 0;
        }

        Object proceed = joinPoint.proceed();

        try {
            System.out.println(localeService.getLocalizedString("testing.timer")
                    + (double) ((System.currentTimeMillis() - startTestTime) / 1000)
                    + " " + localeService.getLocalizedString("testing.timer.sec"));
        } catch (Exception e) {
            // do nothing
        }

        return proceed;
    }
}
