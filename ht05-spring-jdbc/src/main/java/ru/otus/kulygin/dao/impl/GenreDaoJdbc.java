package ru.otus.kulygin.dao.impl;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.kulygin.dao.GenreDao;
import ru.otus.kulygin.dao.impl.mapper.GenreMapper;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.exception.GenreDoesNotExistException;

import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;
    private final GenreMapper genreMapper;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc, GenreMapper genreMapper) {
        this.jdbc = jdbc;
        this.genreMapper = genreMapper;
    }

    @Override
    public boolean existsById(long id) {
        final Integer count = jdbc.queryForObject("select count(*) from Genre where id = :id", Map.of("id", id), Integer.class);
        return count > 0;
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations()
                .queryForObject("select count(*) from Genre", Integer.class);
    }

    @Override
    public void insert(Genre genre) {
        jdbc.update("insert into Genre (id, name) values (:id,:name)",
                Map.of("id", genre.getId(), "name", genre.getName()));
    }

    @Override
    public Genre getById(long id) {
        return jdbc.queryForObject("select id, name from Genre where id = :id", Map.of("id", id), genreMapper);
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, name from Genre", genreMapper);
    }

    @Override
    public void deleteById(long id) {
        if (!existsById(id)) {
            throw new GenreDoesNotExistException("Genre does not exist");
        }
        jdbc.update("delete from Genre where id = :id", Map.of("id", id));
    }

}
