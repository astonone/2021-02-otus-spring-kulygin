package ru.otus.kulygin.enumeration;

import lombok.Getter;

@Getter
public enum WaterTypeEnum {

    TAP_WATER_MOSCOW("Tap water in Moscow"),
    TAP_WATER_MOSCOW_FILTERED("Filtered Tap water in Moscow"),
    ARTESIAN("Artesian"),
    MINERAL_WATER("Mineral Water"),
    WATER_FROM_BAIKAL_LAKE("Water from Baikal lake");

    private final String name;

    WaterTypeEnum(String name) {
        this.name = name;
    }

}
