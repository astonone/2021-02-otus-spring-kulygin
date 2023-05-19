package ru.otus.kulygin.enumeration;

import lombok.Getter;

@Getter
public enum WineTypeEnum {

    ISABELLA_TABLE_WINE_59RUB_LITER("Isabella table wine with cost 59 rubles for 1 liter"),
    CABERNET_SAUVIGNON("Cabernet Sauvignon"),
    SAUVIGNON_BLANC("Sauvignon Blanc"),
    PROSECCO("Prosecco"),
    CABERNET_SAUVIGNON_SCREAMING_EAGLE_1992("Cabernet Sauvignon Screaming Eagle 1992");

    private final String name;

    WineTypeEnum(String name) {
        this.name = name;
    }

}
