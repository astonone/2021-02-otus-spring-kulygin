package ru.otus.kulygin.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.kulygin.domain.Water;
import ru.otus.kulygin.domain.Wine;

import java.util.Collection;

@MessagingGateway
public interface God {

    @Gateway(requestChannel = "waterChannel", replyChannel = "wineChannel")
    Collection<Wine> magic(Collection<Water> waterItems);

}
