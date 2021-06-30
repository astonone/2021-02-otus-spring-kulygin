package ru.otus.kulygin.shell;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.kulygin.domain.Water;
import ru.otus.kulygin.domain.Wine;
import ru.otus.kulygin.enumeration.WaterTypeEnum;
import ru.otus.kulygin.integration.God;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class ShellCommand {

    private final God god;

    @ShellMethod(value = "Transform water to wine(int amountOfWater, int typeOfWater("
            + "\n1 = Tap water in Moscow\n"
            + "2 = Filtered Tap water in Moscow\n"
            + "3 = Artesian\n"
            + "4 = Mineral Water\n"
            + "5 = Water from Baikal lake\n"
            + "6 = Random pack of water(entered amount will be ignored)\n" +
            "))", key = {"tww"})
    public void transformWaterToWine(@ShellOption int amountOfWater, @ShellOption int typeOfWater) {
        Collection<Water> items = generateWaterItems(amountOfWater, typeOfWater);

        System.out.println("New water items: " +
                items.stream().map(water -> water.getAmount() + "L of " + water.getType().getName())
                        .collect(Collectors.joining(",")));

        Collection<Wine> wines = god.magic(items);

        System.out.println("Ready wines: " + wines.stream()
                .map(wine -> wine.getAmount() + "L of " + wine.getType().getName())
                .collect(Collectors.joining(",")));
    }

    private Collection<Water> generateWaterItems(int amountOfWater, int typeOfWater) {
        switch (typeOfWater) {
            case 1:
                return Collections.singletonList(Water.builder()
                        .amount(amountOfWater)
                        .type(WaterTypeEnum.TAP_WATER_MOSCOW)
                        .build());
            case 2:
                return Collections.singletonList(Water.builder()
                        .amount(amountOfWater)
                        .type(WaterTypeEnum.TAP_WATER_MOSCOW_FILTERED)
                        .build());
            case 3:
                return Collections.singletonList(Water.builder()
                        .amount(amountOfWater)
                        .type(WaterTypeEnum.ARTESIAN)
                        .build());
            case 4:
                return Collections.singletonList(Water.builder()
                        .amount(amountOfWater)
                        .type(WaterTypeEnum.MINERAL_WATER)
                        .build());
            case 5:
                return Collections.singletonList(Water.builder()
                        .amount(amountOfWater)
                        .type(WaterTypeEnum.WATER_FROM_BAIKAL_LAKE)
                        .build());
            case 6:
            default:
                return generateRandomWaterItems();
        }
    }

    private Collection<Water> generateRandomWaterItems() {
        List<Water> items = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(1, 10); ++i) {
            items.add(generateWaterItem());
        }
        return items;
    }

    private Water generateWaterItem() {
        final WaterTypeEnum[] waterTypeEnums = {WaterTypeEnum.TAP_WATER_MOSCOW, WaterTypeEnum.TAP_WATER_MOSCOW_FILTERED,
                WaterTypeEnum.ARTESIAN, WaterTypeEnum.MINERAL_WATER, WaterTypeEnum.WATER_FROM_BAIKAL_LAKE};
        return Water.builder()
                .amount(RandomUtils.nextInt(1, 10))
                .type(waterTypeEnums[RandomUtils.nextInt(0, waterTypeEnums.length)])
                .build();
    }

}
