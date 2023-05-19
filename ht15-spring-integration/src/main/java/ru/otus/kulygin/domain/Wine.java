package ru.otus.kulygin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.otus.kulygin.enumeration.WineTypeEnum;

@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Wine {

    private final int amount;
    private final WineTypeEnum type;

}
