package ru.otus.kulygin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.otus.kulygin.enumeration.WaterTypeEnum;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Water {

    private final int amount;
    private final WaterTypeEnum type;

}
