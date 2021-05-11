package ru.otus.kulygin.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.otus.kulygin.domain.Comment;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.dto.ErrorDto;
import ru.otus.kulygin.dto.list.CommentListDto;
import ru.otus.kulygin.repository.BookRepository;
import ru.otus.kulygin.repository.CommentRepository;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyExtractors.toMono;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.BOOK_NOT_FOUND;
import static ru.otus.kulygin.enumerations.ApplicationErrorsEnum.COMMENT_NOT_FOUND;

@Configuration
public class CommentFunctionalConfig {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final MappingService mappingService;

    public CommentFunctionalConfig(BookRepository bookRepository, CommentRepository commentRepository, MappingService mappingService) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
        this.mappingService = mappingService;
    }

    @Bean
    RouterFunction<ServerResponse> getCommentsByBookIdRoute() {
        return route()
                .GET("/api/book/{id}/comment/", accept(APPLICATION_JSON),
                        request -> ok().body(commentRepository.findAllByBook_Id(request.pathVariable("id"))
                                .collect(Collectors.toList())
                                .map(comments -> CommentListDto.builder()
                                        .comments(mappingService.mapAsList(comments, CommentDto.class))
                                        .build()), CommentListDto.class)
                ).build();
    }

    @Bean
    RouterFunction<ServerResponse> addCommentRoute() {
        return route()
                .POST("/api/book/{id}/comment/", accept(APPLICATION_JSON),
                        request -> request.body(toMono(CommentDto.class))
                                .flatMap(commentDto -> bookRepository.findById(request.pathVariable("id"))
                                        .flatMap(book -> commentRepository.save(Comment.builder()
                                                .commentatorName(commentDto.getCommentatorName())
                                                .text(commentDto.getText())
                                                .book(book)
                                                .build())
                                                .map(savedComment -> mappingService.map(savedComment, CommentDto.class))
                                                .flatMap(savedComment -> ok().body(Mono.just(savedComment), CommentDto.class)))
                                        .switchIfEmpty(ServerResponse.status(404).body(Mono.just(ErrorDto.builder()
                                                .code(BOOK_NOT_FOUND.getCode())
                                                .message(BOOK_NOT_FOUND.getMessage())
                                                .build()), ErrorDto.class)))
                ).build();
    }

    @Bean
    RouterFunction<ServerResponse> removeCommentRoute() {
        return route()
                .DELETE("/api/book/{id}/comment/{commentId}", accept(APPLICATION_JSON),
                        request -> bookRepository.existsById(request.pathVariable("id"))
                                .flatMap(isBookExist -> {
                                    if (isBookExist) {
                                        return commentRepository.existsById(request.pathVariable("commentId"))
                                                .flatMap(isCommentExist -> {
                                                    if (isCommentExist) {
                                                        return commentRepository.deleteById(request.pathVariable("commentId"))
                                                                .then(ok().build());
                                                    } else {
                                                        return ServerResponse.status(404).body(Mono.just(ErrorDto.builder()
                                                                .code(COMMENT_NOT_FOUND.getCode())
                                                                .message(COMMENT_NOT_FOUND.getMessage())
                                                                .build()), ErrorDto.class);
                                                    }
                                                });
                                    } else {
                                        return ServerResponse.status(404).body(Mono.just(ErrorDto.builder()
                                                .code(BOOK_NOT_FOUND.getCode())
                                                .message(BOOK_NOT_FOUND.getMessage())
                                                .build()), ErrorDto.class);
                                    }
                                })
                ).build();
    }

}
