package ru.otus.kulygin.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.kulygin.domain.*;
import ru.otus.kulygin.enumerations.UserAuthorities;
import ru.otus.kulygin.repository.*;

import java.util.Arrays;

@ChangeLog(order = "001")
public class DatabaseChangeLog001 {

    @ChangeSet(order = "001", id = "2021-04-12--drop-db--kulygin", author = "viktor.kulygin", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "2021-04-12--insert-genres--kulygin", author = "viktor.kulygin")
    public void insertGenres(GenreRepository genreRepository) {
        val genre1 = Genre.builder()
                .id("1")
                .name("Драма")
                .build();

        val genre2 = Genre.builder()
                .id("2")
                .name("Фантастика")
                .build();

        val genre3 = Genre.builder()
                .id("3")
                .name("Антиутопия")
                .build();

        val genre4 = Genre.builder()
                .id("4")
                .name("Роман")
                .build();

        val genre5 = Genre.builder()
                .id("5")
                .name("Детектив")
                .build();

        val genre6 = Genre.builder()
                .id("6")
                .name("Триллер")
                .build();

        val genre7 = Genre.builder()
                .id("7")
                .name("Ужасы")
                .build();

        val genre8 = Genre.builder()
                .id("8")
                .name("Комедия")
                .build();

        val genre9 = Genre.builder()
                .id("9")
                .name("Фэнтези")
                .build();

        val genre10 = Genre.builder()
                .id("10")
                .name("Научпоп")
                .build();

        genreRepository.saveAll(Arrays.asList(genre1, genre2, genre3, genre4, genre5,
                genre6, genre7, genre8, genre9, genre10));
    }

    @ChangeSet(order = "003", id = "2021-04-12--insert-authors--kulygin", author = "viktor.kulygin")
    public void insertAuthors(AuthorRepository authorRepository) {

        val author = Author.builder()
                .id("1")
                .firstName("Сергей")
                .lastName("Лукьяненко")
                .build();

        val author2 = Author.builder()
                .id("2")
                .firstName("Джордж")
                .lastName("Оруэлл")
                .build();

        val author3 = Author.builder()
                .id("3")
                .firstName("Рэй")
                .lastName("Бредбери")
                .build();

        val author4 = Author.builder()
                .id("4")
                .firstName("Борис")
                .lastName("Акунин")
                .build();

        val author5 = Author.builder()
                .id("5")
                .firstName("Агата")
                .lastName("Кристи")
                .build();

        val author6 = Author.builder()
                .id("6")
                .firstName("Федор")
                .lastName("Достоевский")
                .build();

        val author7 = Author.builder()
                .id("7")
                .firstName("Александр")
                .lastName("Пушкин")
                .build();

        val author8 = Author.builder()
                .id("8")
                .firstName("Лев")
                .lastName("Толстой")
                .build();

        val author9 = Author.builder()
                .id("9")
                .firstName("Джордж")
                .lastName("Мартин")
                .build();

        val author10 = Author.builder()
                .id("10")
                .firstName("Джон")
                .lastName("Толкиен")
                .build();

        authorRepository.saveAll(Arrays.asList(author, author2, author3, author4, author5,
                author6, author7, author8, author9, author10));
    }

    @ChangeSet(order = "004", id = "2021-04-12--insert-books--kulygin", author = "viktor.kulygin")
    public void insertBooks(BookRepository bookRepository) {
        val book = Book.builder()
                .id("1")
                .title("Черновик")
                .genre(Genre.builder()
                        .id("2")
                        .name("Фантастика")
                        .build())
                .author(Author.builder()
                        .id("1")
                        .firstName("Сергей")
                        .lastName("Лукьяненко")
                        .build())
                .build();

        val book2 = Book.builder()
                .id("2")
                .title("Чистовик")
                .genre(Genre.builder()
                        .id("2")
                        .name("Фантастика")
                        .build())
                .author(Author.builder()
                        .id("1")
                        .firstName("Сергей")
                        .lastName("Лукьяненко")
                        .build())
                .build();

        val book3 = Book.builder()
                .id("3")
                .title("1984")
                .genre(Genre.builder()
                        .id("3")
                        .name("Антиутопия")
                        .build())
                .author(Author.builder()
                        .id("2")
                        .firstName("Джордж")
                        .lastName("Оруэлл")
                        .build())
                .build();

        val book4 = Book.builder()
                .id("4")
                .title("Скотный двор")
                .genre(Genre.builder()
                        .id("3")
                        .name("Антиутопия")
                        .build())
                .author(Author.builder()
                        .id("2")
                        .firstName("Джордж")
                        .lastName("Оруэлл")
                        .build())
                .build();

        val book5 = Book.builder()
                .id("5")
                .title("451 градус по фаренгейту")
                .genre(Genre.builder()
                        .id("3")
                        .name("Антиутопия")
                        .build())
                .author(Author.builder()
                        .id("3")
                        .firstName("Рэй")
                        .lastName("Бредбери")
                        .build())
                .build();

        val book6 = Book.builder()
                .id("6")
                .title("Убийство в восточном экспрессе")
                .genre(Genre.builder()
                        .id("5")
                        .name("Детектив")
                        .build())
                .author(Author.builder()
                        .id("5")
                        .firstName("Агата")
                        .lastName("Кристи")
                        .build())
                .build();

        val book7 = Book.builder()
                .id("7")
                .title("Братья Карамазовы")
                .genre(Genre.builder()
                        .id("4")
                        .name("Роман")
                        .build())
                .author(Author.builder()
                        .id("6")
                        .firstName("Федор")
                        .lastName("Достоевский")
                        .build())
                .build();

        val book8 = Book.builder()
                .id("7")
                .title("Турецкий гамбит")
                .genre(Genre.builder()
                        .id("5")
                        .name("Детектив")
                        .build())
                .author(Author.builder()
                        .id("4")
                        .firstName("Борис")
                        .lastName("Акунин")
                        .build())
                .build();

        val book9 = Book.builder()
                .id("9")
                .title("Властелин колец")
                .genre(Genre.builder()
                        .id("9")
                        .name("Фэнтези")
                        .build())
                .author(Author.builder()
                        .id("10")
                        .firstName("Джон")
                        .lastName("Толкиен")
                        .build())
                .build();

        val book10 = Book.builder()
                .id("10")
                .title("Песнь льда и пламени: Ветра зимы")
                .genre(Genre.builder()
                        .id("9")
                        .name("Фэнтези")
                        .build())
                .author(Author.builder()
                        .id("9")
                        .firstName("Джордж")
                        .lastName("Мартин")
                        .build())
                .build();

        bookRepository.saveAll(Arrays.asList(book, book2, book3, book4, book5,
                book6, book7, book8, book9, book10));
    }

    @ChangeSet(order = "005", id = "2021-04-12--insert-comments--kulygin", author = "viktor.kulygin")
    public void insertComments(CommentRepository commentRepository) {
        val comment = Comment.builder()
                .commentatorName("Вася Пупкин")
                .text("Книга бомба!")
                .book(Book.builder()
                        .id("1")
                        .build())
                .build();

        val comment2 = Comment.builder()
                .commentatorName("Саня Петров")
                .text("Первая часть была лучше")
                .book(Book.builder()
                        .id("2")
                        .build())
                .build();

        val comment3 = Comment.builder()
                .commentatorName("Володя Путин")
                .text("Лол, жиза! У меня в стране также")
                .book(Book.builder()
                        .id("3")
                        .build())
                .build();

        commentRepository.saveAll(Arrays.asList(comment, comment2, comment3));
    }

    @ChangeSet(order = "006", id = "2021-05-24--insert-user--kulygin", author = "viktor.kulygin")
    public void insertUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        val user = User.builder()
                .username("astonone")
                .password(passwordEncoder.encode("123"))
                .authority(UserAuthorities.USER.getAuthority())
                .enabled(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .build();

        val admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("777"))
                .authority(UserAuthorities.ADMIN.getAuthority())
                .enabled(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .build();

        userRepository.saveAll(Arrays.asList(user, admin));
    }

}