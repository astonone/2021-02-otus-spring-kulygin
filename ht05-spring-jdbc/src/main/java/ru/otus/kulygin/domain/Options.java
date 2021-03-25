package ru.otus.kulygin.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Options {

    private boolean isPartialLoading;

}
