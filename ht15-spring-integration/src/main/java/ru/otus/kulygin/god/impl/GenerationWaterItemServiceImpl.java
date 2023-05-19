package ru.otus.kulygin.god.impl;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Water;
import ru.otus.kulygin.enumeration.WaterTypeEnum;
import ru.otus.kulygin.god.GenerationWaterItemService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenerationWaterItemServiceImpl implements GenerationWaterItemService {

    private static final Map<Integer, Collection<Water>> waterItems = Map.of(
            1, Collections.singletonList(Water.builder()
                    .type(WaterTypeEnum.TAP_WATER_MOSCOW)
                    .build()),
            2, Collections.singletonList(Water.builder()
                    .type(WaterTypeEnum.TAP_WATER_MOSCOW_FILTERED)
                    .build()),
            3, Collections.singletonList(Water.builder()
                    .type(WaterTypeEnum.ARTESIAN)
                    .build()),
            4, Collections.singletonList(Water.builder()
                    .type(WaterTypeEnum.MINERAL_WATER)
                    .build()),
            5, Collections.singletonList(Water.builder()
                    .type(WaterTypeEnum.WATER_FROM_BAIKAL_LAKE)
                    .build()));
    public static final int RANDOM_WATER_ITEM_TYPE = 6;

    @Override
    public Collection<Water> generateWaterItems(int amountOfWater, int typeOfWater) {
        Collection<Water> item = waterItems.getOrDefault(typeOfWater, generateRandomWaterItems());
        return typeOfWater == RANDOM_WATER_ITEM_TYPE ? item : item.stream()
                .filter(i -> i.getAmount() == 0)
                .map(i -> Water.builder().type(i.getType()).amount(amountOfWater).build())
                .collect(Collectors.toList());
    }

    private Collection<Water> generateRandomWaterItems() {
        List<Water> items = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(2, 10); ++i) {
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
