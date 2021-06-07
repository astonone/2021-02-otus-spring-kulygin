package ru.otus.kulygin.service;

import ru.otus.kulygin.domain.mongodb.CommentDocument;
import ru.otus.kulygin.domain.rdb.Comment;

public interface CommentTransformerService {

    CommentDocument transform(Comment comment);

}
