package ru.otus.kulygin.event;

import lombok.val;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.repository.CommentRepository;

import java.util.Objects;

@Component
public class CascadeCommentDeleteMongoEventListener extends AbstractMongoEventListener<Object> {

    private final CommentRepository commentRepository;

    public CascadeCommentDeleteMongoEventListener(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Object> event) {
        if (Objects.equals(event.getCollectionName(), "book")) {
            String id = String.valueOf(event.getSource().get("_id"));

            val commentsByBook_id = commentRepository.findAllByBook_Id(id);
            commentRepository.deleteAll(commentsByBook_id);
        }
    }

}
