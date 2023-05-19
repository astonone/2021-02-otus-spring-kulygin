package ru.otus.kulygin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.exception.EntityPrettyPrintException;
import ru.otus.kulygin.service.ObjectPrettyConverterService;

@Service
public class ObjectPrettyConverterServiceJson implements ObjectPrettyConverterService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String getPrettyString(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new EntityPrettyPrintException("Error while printing object: ", e);
        }
    }

}
