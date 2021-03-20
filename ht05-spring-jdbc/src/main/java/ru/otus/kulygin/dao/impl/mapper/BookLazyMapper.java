package ru.otus.kulygin.dao.impl.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.otus.kulygin.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BookLazyMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        long book_id = resultSet.getLong("id");
        String title = resultSet.getString("title");

        return Book.builder()
                .id(book_id)
                .title(title)
                .build();
    }

}
