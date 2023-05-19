package ru.otus.kulygin.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.kulygin.dto.CommentDto;
import ru.otus.kulygin.service.BookService;
import ru.otus.kulygin.service.CommentService;

@Controller
public class CommentController {

    private final BookService bookService;
    private final CommentService commentService;

    public CommentController(BookService bookService, CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    @GetMapping("/book-comments")
    public String commentsBookPage(@RequestParam("id") String id, Model model) {
        model.addAttribute("comments", commentService.findAllByBookId(id));
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
        model.addAttribute("comment", new CommentDto());
        model.addAttribute("bookId", bookId);
        return "book-comment-create";
    }

    @PostMapping("/book-comment-create")
    public String createComment(@RequestParam("bookId") String bookId, CommentDto comment) {
        bookService.addCommentToBook(comment.getCommentatorName(), comment.getText(), bookId);
        return "redirect:/book-comments?id=" + bookId;
    }

}
