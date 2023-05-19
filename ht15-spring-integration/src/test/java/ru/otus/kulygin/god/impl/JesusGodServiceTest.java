package ru.otus.kulygin.god.impl;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.kulygin.domain.Water;
import ru.otus.kulygin.domain.Wine;
import ru.otus.kulygin.enumeration.WaterTypeEnum;
import ru.otus.kulygin.enumeration.WineTypeEnum;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = JesusGodService.class)
class JesusGodServiceTest {

    @Autowired
    private JesusGodService jesusGodService;

    @ParameterizedTest
    @MethodSource("provideWineAndWaterForMagic")
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(Water water, Wine wine) {
        assertEquals(wine, jesusGodService.magic(water));
    }

    private static Stream<Arguments> provideWineAndWaterForMagic() {
        return Stream.of(
                Arguments.of(Water.builder()
                                .amount(1)
                                .type(WaterTypeEnum.TAP_WATER_MOSCOW)
                                .build(),
                        Wine.builder()
                                .amount(1)
                                .type(WineTypeEnum.ISABELLA_TABLE_WINE_59RUB_LITER)
                                .build()),
                Arguments.of(Water.builder()
                                .amount(4)
                                .type(WaterTypeEnum.TAP_WATER_MOSCOW_FILTERED)
                                .build(),
                        Wine.builder()
                                .amount(4)
                                .type(WineTypeEnum.CABERNET_SAUVIGNON)
                                .build()),
                Arguments.of(Water.builder()
                                .amount(5)
                                .type(WaterTypeEnum.ARTESIAN)
                                .build(),
                        Wine.builder()
                                .amount(5)
                                .type(WineTypeEnum.SAUVIGNON_BLANC)
                                .build()),
                Arguments.of(Water.builder()
                                .amount(2)
                                .type(WaterTypeEnum.MINERAL_WATER)
                                .build(),
                        Wine.builder()
                                .amount(2)
                                .type(WineTypeEnum.PROSECCO)
                                .build()),
                Arguments.of(Water.builder()
                                .amount(8)
                                .type(WaterTypeEnum.WATER_FROM_BAIKAL_LAKE)
                                .build(),
                        Wine.builder()
                                .amount(8)
                                .type(WineTypeEnum.CABERNET_SAUVIGNON_SCREAMING_EAGLE_1992)
                                .build())
        );
    }

}