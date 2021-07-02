package ru.otus.kulygin.god;

import ru.otus.kulygin.domain.Water;

import java.util.Collection;

public interface GenerationWaterItemService {
    Collection<Water> generateWaterItems(int amountOfWater, int typeOfWater);
}
