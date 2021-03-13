package ru.otus.kulygin.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.kulygin.service.LocaleService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName(value = "TimerAspect should ")
class TimerAspectTest {

    @Configuration
    static class NestedConfiguration {
        @Bean
        LocaleService localeService() {
            return mock(LocaleService.class);
        }

        @Bean
        TimerAspect timerAspect() {
            return new TimerAspect(localeService());
        }
    }

    @Autowired
    private TimerAspect timerAspect;

    @Autowired
    private LocaleService localeService;

    @Test
    @DisplayName(value = " run aspect without errors")
    public void shouldDoRunAspectWithoutErrors() throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        when(joinPoint.proceed()).thenReturn(new Object());

        final Object result = timerAspect.logTimeOfTesting(joinPoint);

        assertThat(result).isNotNull();
        verify(localeService).getLocalizedString(eq("testing.timer"), anyString());
        verify(localeService).getLocalizedString("testing.timer.sec");
    }

}