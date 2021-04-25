package ru.otus.kulygin.web;

import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.kulygin.domain.Book;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.exception.BookDoesNotExistException;
import ru.otus.kulygin.service.BookService;
import ru.otus.kulygin.service.CommentService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

@Controller
public class CommentController {

    private final BookService bookService;
    private final CommentService commentService;
    private final MappingService mappingService;

    public CommentController(BookService bookService, CommentService commentService, MappingService mappingService) {
        this.bookService = bookService;
        this.commentService = commentService;
        this.mappingService = mappingService;
    }

    @GetMapping("/book-comments")
    public String commentsBookPage(@RequestParam("id") String id, Model model) {
        val comments = commentService.findAllByBookId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("bookId", id);
        return "book-comments";
    }

    @PostMapping("/delete-comment")
    public String deleteComment(@RequestParam("commentId") String commentId, @RequestParam("bookId") String bookId) {
        bookService.removeCommentFromBook(commentId);
        return "redirect:/book-comments?id=" + bookId;
    }

    @GetMapping("/book-comment-create")
    public String createComment(@RequestParam("id") String bookId, Model model) {
        CommentDto commentDto = new CommentDto();
        model.addAttribute("comment", commentDto);
        model.addAttribute("bookId", bookId);
        return "book-comment-create";
    }

    @PostMapping("/book-comment-create")
    public String createComment(@RequestParam("bookId") String bookId, CommentDto comment) {
        val book = bookService.getById(bookId);
        bookService.addCommentToBook(comment.getCommentatorName(), comment.getText(), book
                .map(bookDto -> mappingService.map(bookDto, Book.class))
                .orElseThrow(() -> new BookDoesNotExistException("Book with id=" + bookId + " has not found")));
        return "redirect:/book-comments?id=" + bookId;
    }

}
