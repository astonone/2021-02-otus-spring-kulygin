package ru.otus.kulygin.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.service.AuthorService;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/author-list")
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAll());
        return "author-list";
    }

    @GetMapping("/edit-author")
    public String editAuthorPage(@RequestParam("id") String id, Model model) {
        model.addAttribute("author", authorService.getById(id)
                .orElseThrow(() -> new AuthorDoesNotExistException("Author with id=" + id + " has not found")));
        return "author-edit";
    }

    @GetMapping("/create-author")
    public String createAuthorPage(Model model) {
        model.addAttribute("author", new AuthorDto());
        return "author-create";
    }

    @PostMapping("/edit-author")
    public String editAuthor(AuthorDto author) {
        authorService.save(author);
        return "redirect:/author-list";
    }

    @PostMapping("/create-author")
    public String createAuthor(AuthorDto author) {
        authorService.save(author);
        return "redirect:/author-list";
    }

    @PostMapping("/delete-author")
    public String deleteAuthor(@RequestParam("id") String id) {
        authorService.deleteById(id);
        return "redirect:/author-list";
    }

}
