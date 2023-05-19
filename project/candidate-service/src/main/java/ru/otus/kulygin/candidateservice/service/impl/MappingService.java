package ru.otus.kulygin.candidateservice.service.impl;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.candidateservice.domain.Candidate;
import ru.otus.kulygin.candidateservice.dto.CandidateDto;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class MappingService extends ConfigurableMapper {

    private MapperFactory factory;

    @Override
    public void configure(MapperFactory factory) {
        this.factory = factory;
    }

    private void registerCustomMappers(MapperFactory factory) {
        factory.classMap(Candidate.class, CandidateDto.class)
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(Candidate obj, CandidateDto dto, MappingContext context) {
                        super.mapAtoB(obj, dto, context);

                        if (obj != null && obj.getPathToCvFile() != null) {
                            InputStream in;
                            byte[] bytes = new byte[0];
                            try {
                                in = new FileInputStream(obj.getPathToCvFile());
                                bytes = IOUtils.toByteArray(in);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            dto.setCvFile(bytes);
                        }
                    }
                })
                .byDefault()
                .register();
    }

    @PostConstruct
    public void registerStuff() {
        registerCustomMappers(factory);
    }

}
