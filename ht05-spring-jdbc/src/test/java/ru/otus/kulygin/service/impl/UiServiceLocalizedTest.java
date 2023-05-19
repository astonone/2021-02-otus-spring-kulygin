package ru.otus.kulygin.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.service.LocaleService;
import ru.otus.kulygin.service.ObjectPrettyConverterService;
import ru.otus.kulygin.service.UiService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = UiServiceLocalized.class)
@DisplayName(value = "UiLocalizedFacadeImpl should ")
class UiServiceLocalizedTest {

    public static final String TEST_LANGUAGE_KEY = "test.key";
    public static final String TEST_LANGUAGE_VALUE = "hello";
    public static final String PRETTY_STRING = "{  \"id\" : 1,  \"name\" : \"Fantasy\"}";

    @Autowired
    private UiService uiService;

    @MockBean
    private LocaleService localeService;

    @MockBean
    private ObjectPrettyConverterService objectPrettyConverterService;

    @Test
    @DisplayName(value = "get localized message for user")
    void shouldShowLocalizedMessageForUser() {
        when(localeService.getLocalizedString(TEST_LANGUAGE_KEY)).thenReturn(TEST_LANGUAGE_VALUE);

        final String localizedMessageForUser = uiService.getLocalizedMessageForUser(TEST_LANGUAGE_KEY);

        verify(localeService).getLocalizedString(TEST_LANGUAGE_KEY);
        assertThat(localizedMessageForUser).isEqualTo(TEST_LANGUAGE_VALUE);
    }

    @Test
    @DisplayName(value = "get pretty formatted object for user")
    void shouldGetObjectForPrettyPrint() {
        final Genre fantasy = Genre.builder()
                .id(1)
                .name("Fantasy")
                .build();
        when(objectPrettyConverterService.getPrettyString(fantasy)).thenReturn(PRETTY_STRING);

        final String forPrettyPrint = uiService.getObjectForPrettyPrint(fantasy);

        verify(objectPrettyConverterService).getPrettyString(fantasy);
        assertThat(forPrettyPrint.replace("\r","").replace("\n","")).isEqualTo(PRETTY_STRING);
    }

}