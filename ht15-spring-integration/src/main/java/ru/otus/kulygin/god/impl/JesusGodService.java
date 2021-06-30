package ru.otus.kulygin.god.impl;

import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Water;
import ru.otus.kulygin.domain.Wine;
import ru.otus.kulygin.enumeration.WineTypeEnum;
import ru.otus.kulygin.god.GodService;

@Service
public class JesusGodService implements GodService {

    public static final int DEFAULT_WINE_AMOUNT = 1000;

    public Wine magic(Water water) {
        System.out.println("Transforming " + water.getAmount() + "L of " + water.getType().getName());

        Wine wine;

        try {
            Thread.sleep(2000);
            switch (water.getType()) {
                case TAP_WATER_MOSCOW_FILTERED:
                    wine = Wine.builder()
                            .amount(water.getAmount())
                            .type(WineTypeEnum.CABERNET_SAUVIGNON)
                            .build();
                    break;
                case ARTESIAN:
                    wine = Wine.builder()
                            .amount(water.getAmount())
                            .type(WineTypeEnum.SAUVIGNON_BLANC)
                            .build();
                    break;
                case MINERAL_WATER:
                    wine = Wine.builder()
                            .amount(water.getAmount())
                            .type(WineTypeEnum.PROSECCO)
                            .build();
                    break;
                case WATER_FROM_BAIKAL_LAKE:
                    wine = Wine.builder()
                            .amount(water.getAmount())
                            .type(WineTypeEnum.CABERNET_SAUVIGNON_SCREAMING_EAGLE_1992)
                            .build();
                    break;
                case TAP_WATER_MOSCOW:
                default:
                    wine = Wine.builder()
                            .amount(water.getAmount())
                            .type(WineTypeEnum.ISABELLA_TABLE_WINE_59RUB_LITER)
                            .build();
            }
        } catch (InterruptedException e) {
            wine = Wine.builder()
                    .amount(DEFAULT_WINE_AMOUNT)
                    .type(WineTypeEnum.ISABELLA_TABLE_WINE_59RUB_LITER)
                    .build();
            System.out.println("Magic error");
        }

        System.out.println(water.getAmount() + "L of " + water.getType().getName() + " transformed to "
                + water.getAmount() + "L of " + wine.getType().getName());

        return wine;
    }

}
