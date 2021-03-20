package ru.otus.kulygin.dao.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.kulygin.dao.AuthorDao;
import ru.otus.kulygin.dao.impl.mapper.AuthorMapper;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorMapper authorMapper;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc, AuthorMapper authorMapper) {
        this.jdbc = jdbc;
        this.authorMapper = authorMapper;
    }

    @Override
    public boolean existsById(long id) {
        final Integer count = jdbc.queryForObject("select count(*) from Author where id = :id", Map.of("id", id), Integer.class);
        return count > 0;
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations()
                .queryForObject("select count(*) from Author", Integer.class);
    }

    @Override
    public void insert(Author author) {
        jdbc.update("insert into Author (id, first_name, last_name) values (:id, :first_name, :last_name)",
                Map.of("id", author.getId(),
                        "first_name", author.getFirstName(),
                        "last_name", author.getLastName()));
    }

    @Override
    public Author getById(long id) {
        return jdbc.queryForObject("select id, first_name, last_name from Author where id = :id", Map.of("id", id), authorMapper);
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, first_name, last_name from Author", authorMapper);
    }

    @Override
    public void deleteById(long id) {
        if (!existsById(id)) {
            throw new AuthorDoesNotExistException("Author does not exist");
        }
        jdbc.update("delete from Author where id = :id", Map.of("id", id));
    }

}
