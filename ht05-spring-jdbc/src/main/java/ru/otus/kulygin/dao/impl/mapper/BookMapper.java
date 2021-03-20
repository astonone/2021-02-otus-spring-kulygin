package ru.otus.kulygin.dao.impl.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        long book_id = resultSet.getLong("book_id");
        String title = resultSet.getString("title");
        long genreId = resultSet.getLong("genre_id");
        String genreName = resultSet.getString("genre_name");
        long authorId = resultSet.getLong("author_id");
        String authorFirstName = resultSet.getString("author_first_name");
        String authorLastName = resultSet.getString("author_last_name");
        return new Book(book_id, title,
                Author.builder()
                        .id(authorId)
                        .firstName(authorFirstName)
                        .lastName(authorLastName)
                        .build(),
                Genre.builder()
                        .id(genreId)
                        .name(genreName)
                        .build());
    }

}
