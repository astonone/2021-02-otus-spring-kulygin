package ru.otus.kulygin.event;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.repository.BookRepository;

@Component
public class CascadeGenreDeleteMongoEventListener extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepository;

    public CascadeGenreDeleteMongoEventListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        String id = String.valueOf(event.getSource().get("_id"));
        if (bookRepository.existsByGenre_Id(id)) {
            throw new RuntimeException("Genre has related books");
        }
    }

}
