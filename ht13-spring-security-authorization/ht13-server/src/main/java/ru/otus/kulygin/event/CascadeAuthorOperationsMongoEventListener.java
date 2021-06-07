package ru.otus.kulygin.event;

import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.repository.BookRepository;

@Component
public class CascadeAuthorOperationsMongoEventListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;

    public CascadeAuthorOperationsMongoEventListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Author> event) {
        String id = String.valueOf(event.getSource().get("_id"));
        if (bookRepository.existsByAuthor_Id(id)) {
            throw new RuntimeException("Author has related books");
        }
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Author> event) {
        val author = event.getSource();
        val booksByAuthor = bookRepository.findAllByAuthor_Id(author.getId());
        booksByAuthor.forEach(book -> book.setAuthor(author));
        if (!booksByAuthor.isEmpty()) {
            bookRepository.saveAll(booksByAuthor);
        }
    }

}
