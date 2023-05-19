package ru.otus.kulygin.event;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.repository.BookRepository;

@Component
public class CascadeAuthorDeleteMongoEventListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;

    public CascadeAuthorDeleteMongoEventListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        String id = String.valueOf(event.getSource().get("_id"));
        if (bookRepository.existsByAuthor_Id(id)) {
            throw new RuntimeException("Author has related books");
        }
    }

}
