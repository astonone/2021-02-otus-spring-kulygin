package ru.otus.kulygin.event;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.repository.CommentRepository;

@Component
public class CascadeCommentDeleteMongoEventListener extends AbstractMongoEventListener<Book> {

    private final CommentRepository commentRepository;

    public CascadeCommentDeleteMongoEventListener(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Book> event) {
        String id = String.valueOf(event.getSource().get("_id"));
        commentRepository.deleteAllByBook_Id(id);
    }

}
