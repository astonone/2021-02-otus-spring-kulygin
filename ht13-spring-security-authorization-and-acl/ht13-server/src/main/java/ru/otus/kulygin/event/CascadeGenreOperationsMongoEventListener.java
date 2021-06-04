package ru.otus.kulygin.event;

import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.repository.BookRepository;

@Component
public class CascadeGenreOperationsMongoEventListener extends AbstractMongoEventListener<Genre> {

    private final BookRepository bookRepository;

    public CascadeGenreOperationsMongoEventListener(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Genre> event) {
        String id = String.valueOf(event.getSource().get("_id"));
        if (bookRepository.existsByGenre_Id(id)) {
            throw new RuntimeException("Genre has related books");
        }
    }

    @Override
    public void onAfterSave(AfterSaveEvent<Genre> event) {
        val genre = event.getSource();
        val booksByGenre = bookRepository.findAllByGenre_Id(genre.getId());
        booksByGenre.forEach(book -> book.setGenre(genre));
        if (!booksByGenre.isEmpty()) {
            bookRepository.saveAll(booksByGenre);
        }
    }
}
