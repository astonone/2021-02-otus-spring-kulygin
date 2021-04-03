package ru.otus.kulygin.dao.impl;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.kulygin.dao.CommentDao;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.exception.CommentDoesNotExistException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Dao for working with comments ")
@DataJpaTest
@Import({CommentDaoJpa.class})
class CommentDaoJpaTest {

    public static final long EXISTED_COMMENT_ID = 3;
    public static final long NOT_EXISTED_COMMENT_ID = 999;
    public static final long EXISTED_BOOK_ID = 4;
    public static final long EXISTED_BOOK_ID_WITH_COMMENTS = 3;
    public static final String COMMENTATOR_NAME = "Владимир Путин";
    public static final String TEXT = "Очень нравится эта книга! Безумно переживал за свиней";

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("should return true for existed entity")
    void shouldReturnTrueForExistedEntity() {
        assertThat(commentDao.existsById(EXISTED_COMMENT_ID)).isTrue();
    }

    @Test
    @DisplayName("should return false for existed entity")
    void shouldReturnFalseForExistedEntity() {
        assertThat(commentDao.existsById(NOT_EXISTED_COMMENT_ID)).isFalse();
    }

    @Test
    @DisplayName("return expected comment by id")
    void shouldReturnExpectedCommentById() {
        val optionalActualComment = commentDao.getById(EXISTED_COMMENT_ID);
        val expectedComment = em.find(Comment.class, EXISTED_COMMENT_ID);
        assertThat(optionalActualComment).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("not return expected comment by id because comment does not exist")
    void shouldNotReturnExpectedCommentById() {
        assertThat(commentDao.getById(NOT_EXISTED_COMMENT_ID).isEmpty()).isTrue();
    }

    @Test
    @DisplayName("add author to database")
    void shouldInsertComment() {
        val book = em.find(Book.class, EXISTED_BOOK_ID);
        val comment = Comment.builder()
                .commentatorName(COMMENTATOR_NAME)
                .text(TEXT)
                .book(book)
                .build();

        commentDao.insert(comment);
        assertThat(comment.getId()).isNotNull();

        val actualComment = em.find(Comment.class, comment.getId());
        assertThat(actualComment).isNotNull()
                .matches(c -> c.getCommentatorName().equals(COMMENTATOR_NAME))
                .matches(c -> c.getText().equals(TEXT))
                .matches(c -> c.getBook().equals(book));
    }

    @Test
    @DisplayName("remove comment by id")
    void shouldCorrectDeleteCommentById() {
        val firstComment = em.find(Comment.class, EXISTED_COMMENT_ID);
        assertThat(firstComment).isNotNull();
        em.detach(firstComment);

        commentDao.deleteById(EXISTED_COMMENT_ID);
        val deletedComment = em.find(Comment.class, EXISTED_COMMENT_ID);

        assertThat(deletedComment).isNull();
    }

    @Test
    @DisplayName("not remove comment by id because comment does not exist")
    void shouldNotCorrectDeleteCommentByIdBecauseCommentDoesNotExist() {
        assertThatThrownBy(() -> commentDao.deleteById(NOT_EXISTED_COMMENT_ID))
                .isInstanceOf(CommentDoesNotExistException.class);
    }

    @Test
    @DisplayName("find all comments by book id")
    void shouldFindCommentsByBookId() {
        val commentTypedQuery =
                em.getEntityManager().createQuery("select c from Comment c where c.book.id = :bookId", Comment.class);
        commentTypedQuery.setParameter("bookId", EXISTED_BOOK_ID_WITH_COMMENTS);
        val actualCommentsList = commentTypedQuery.getResultList();

        val expectedCommentsList = commentDao.findAllByBookId(EXISTED_BOOK_ID_WITH_COMMENTS);

        assertThat(expectedCommentsList)
                .isNotEmpty()
                .hasSize(1)
                .isEqualTo(actualCommentsList);
    }

}