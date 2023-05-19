package ru.otus.kulygin.god.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.kulygin.domain.Water;
import ru.otus.kulygin.enumeration.WaterTypeEnum;
import ru.otus.kulygin.god.GenerationWaterItemService;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = GenerationWaterItemServiceImpl.class)
class GenerationWaterItemServiceImplTest {

    @Autowired
    private GenerationWaterItemService generationWaterItemService;

    @ParameterizedTest
    @MethodSource("provideWaterItemsForGenerator")
    public void isBlank_ShouldReturnTrueForNullOrBlankStrings(Collection<Water> waterItems, int amountOfWater, int typeOfWater) {
        assertEquals(waterItems, generationWaterItemService.generateWaterItems(amountOfWater, typeOfWater));
    }

    @Test
    public void shouldGenerateRandomWaterItems() {
        final Collection<Water> waters = generationWaterItemService.generateWaterItems(0, 6);
        assertTrue(waters.size() >= 2);
    }

    private static Stream<Arguments> provideWaterItemsForGenerator() {
        return Stream.of(
                Arguments.of(Collections.singletonList(Water.builder()
                        .amount(3)
                        .type(WaterTypeEnum.TAP_WATER_MOSCOW)
                        .build()), 3, 1),
                Arguments.of(Collections.singletonList(Water.builder()
                        .amount(2)
                        .type(WaterTypeEnum.TAP_WATER_MOSCOW_FILTERED)
                        .build()), 2, 2),
                Arguments.of(Collections.singletonList(Water.builder()
                        .amount(1)
                        .type(WaterTypeEnum.ARTESIAN)
                        .build()), 1, 3),
                Arguments.of(Collections.singletonList(Water.builder()
                        .amount(4)
                        .type(WaterTypeEnum.MINERAL_WATER)
                        .build()), 4, 4),
                Arguments.of(Collections.singletonList(Water.builder()
                        .amount(7)
                        .type(WaterTypeEnum.WATER_FROM_BAIKAL_LAKE)
                        .build()), 7, 5));
    }
}