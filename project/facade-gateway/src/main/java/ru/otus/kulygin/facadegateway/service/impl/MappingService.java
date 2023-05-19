package ru.otus.kulygin.facadegateway.service.impl;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Service;

@Service
public class MappingService extends ConfigurableMapper {

    private MapperFactory factory;

    @Override
    public void configure(MapperFactory factory) {
        this.factory = factory;
    }
}
