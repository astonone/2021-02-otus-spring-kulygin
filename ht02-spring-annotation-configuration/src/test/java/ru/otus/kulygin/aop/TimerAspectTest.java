package ru.otus.kulygin.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.kulygin.service.LocaleService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName(value = "TimerAspect should ")
class TimerAspectTest {

    private final LocaleService localeService = mock(LocaleService.class);
    private final TimerAspect timerAspect = new TimerAspect(localeService);

    @Test
    @DisplayName(value = " run aspect without errors")
    public void shouldDoRunAspectWithoutErrors() throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        when(joinPoint.proceed()).thenReturn(new Object());

        final Object result = timerAspect.logTimeOfTesting(joinPoint);

        assertThat(result).isNotNull();
        verify(localeService).getLocalizedString("testing.timer");
        verify(localeService).getLocalizedString("testing.timer.sec");
    }
}