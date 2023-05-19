package ru.otus.kulygin.dao.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.kulygin.dao.BookDao;
import ru.otus.kulygin.dao.impl.mapper.BookLazyMapper;
import ru.otus.kulygin.dao.impl.mapper.BookMapper;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Options;
import ru.otus.kulygin.exception.BookDoesNotExistException;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final BookMapper bookMapper;
    private final BookLazyMapper bookLazyMapper;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc, BookMapper bookMapper, BookLazyMapper bookLazyMapper) {
        this.jdbc = jdbc;
        this.bookMapper = bookMapper;
        this.bookLazyMapper = bookLazyMapper;
    }

    @Override
    public boolean existsById(long id) {
        final Integer count = jdbc.queryForObject("select count(*) from Book where id = :id", Map.of("id", id), Integer.class);
        return count > 0;
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations()
                .queryForObject("select count(*) from Book", Integer.class);
    }

    @Override
    public void insert(Book book) {
        jdbc.update("insert into Book (id, title, author_id, genre_id) values (:id, :title, :author_id, :genre_id)",
                Map.of("id", book.getId(),
                        "title", book.getTitle(),
                        "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId()));
    }

    @Override
    public Book getById(long id, Options options) {
        return options.isPartialLoading() ? jdbc.queryForObject("select id, title from Book where id = :id", Map.of("id", id), bookLazyMapper)
                : jdbc.queryForObject("select b.id as book_id, title, g.id as genre_id, g.name as genre_name"
                + ", a.id as author_id, a.first_name as author_first_name, a.last_name as author_last_name"
                + " from Book b left join Genre g on b.genre_id=g.id left join Author a on a.id=b.author_id where b.id = :id", Map.of("id", id), bookMapper);
    }

    @Override
    public List<Book> getAll(Options options) {
        return options.isPartialLoading() ? jdbc.query("select id, title from Book", bookLazyMapper)
                : jdbc.query("select b.id as book_id, title, g.id as genre_id, g.name as genre_name"
                + ", a.id as author_id, a.first_name as author_first_name, a.last_name as author_last_name"
                + " from Book b left join Genre g on b.genre_id=g.id left join Author a on a.id=b.author_id", bookMapper);
    }

    @Override
    public void deleteById(long id) {
        if (!existsById(id)) {
            throw new BookDoesNotExistException("Book does not exist");
        }
        jdbc.update("delete from Book where id = :id", Map.of("id", id));
    }

}
