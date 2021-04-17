package ru.otus.kulygin.service.impl.mapping;

import lombok.val;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.repository.CommentRepository;

import javax.annotation.PostConstruct;

@Service
public class MappingService extends ConfigurableMapper {

    private final CommentRepository commentRepository;

    private MapperFactory factory;

    public MappingService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void configure(MapperFactory factory) {
        this.factory = factory;
    }

    private void registerCustomMappers(MapperFactory factory) {
        factory.classMap(Book.class, BookDto.class)
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(Book obj, BookDto dto, MappingContext context) {
                        super.mapAtoB(obj, dto, context);

                        if (obj != null) {
                            val commentsByBookId = commentRepository.findAllByBook_Id(obj.getId());

                            val mapperFacade = factory.getMapperFacade();
                            dto.setComments(mapperFacade.mapAsList(commentsByBookId, CommentDto.class));
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
