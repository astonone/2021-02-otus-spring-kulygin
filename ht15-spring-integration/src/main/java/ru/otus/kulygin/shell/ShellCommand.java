package ru.otus.kulygin.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.kulygin.domain.Water;
import ru.otus.kulygin.domain.Wine;
import ru.otus.kulygin.god.GenerationWaterItemService;
import ru.otus.kulygin.integration.God;

import java.util.Collection;
import java.util.stream.Collectors;

@ShellComponent
@AllArgsConstructor
public class ShellCommand {

    private final God god;
    private final GenerationWaterItemService generationWaterItemService;

    @ShellMethod(value = "Transform water to wine(int amountOfWater, int typeOfWater("
            + "\n1 = Tap water in Moscow\n"
            + "2 = Filtered Tap water in Moscow\n"
            + "3 = Artesian\n"
            + "4 = Mineral Water\n"
            + "5 = Water from Baikal lake\n"
            + "6 = Random pack of water(entered amount will be ignored)\n" +
            "))", key = {"tww"})
    public void transformWaterToWine(@ShellOption int amountOfWater, @ShellOption int typeOfWater) {
        Collection<Water> items = generationWaterItemService.generateWaterItems(amountOfWater, typeOfWater);

        System.out.println("New water items: " +
                items.stream().map(water -> water.getAmount() + "L of " + water.getType().getName())
                        .collect(Collectors.joining(",")));

        Collection<Wine> wines = god.magic(items);

        System.out.println("Ready wines: " + wines.stream()
                .map(wine -> wine.getAmount() + "L of " + wine.getType().getName())
                .collect(Collectors.joining(",")));
    }
}
