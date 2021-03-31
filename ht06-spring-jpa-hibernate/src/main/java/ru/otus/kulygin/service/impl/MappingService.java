package ru.otus.kulygin.service.impl;

import lombok.val;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Service;
import ru.otus.kulygin.dao.CommentDao;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.dto.BookDto;
import ru.otus.kulygin.dto.CommentDto;

import javax.annotation.PostConstruct;

@Service
public class MappingService extends ConfigurableMapper {

    private final CommentDao commentDao;

    private MapperFactory factory;

    public MappingService(CommentDao commentDao) {
        this.commentDao = commentDao;
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
                            val commentsByBookId = commentDao.findAllByBookId(obj.getId());

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
