package ru.otus.kulygin.web;

import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.kulygin.domain.Author;
import ru.otus.kulygin.dto.AuthorDto;
import ru.otus.kulygin.exception.AuthorDoesNotExistException;
import ru.otus.kulygin.service.AuthorService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

import java.util.Optional;

@Controller
public class AuthorController {

    private final AuthorService authorService;
    private final MappingService mappingService;

    public AuthorController(AuthorService authorService, MappingService mappingService) {
        this.authorService = authorService;
        this.mappingService = mappingService;
    }

    @GetMapping("/author-list")
    public String listAuthors(Model model) {
        val authors = authorService.getAll();
        model.addAttribute("authors", authors);
        return "author-list";
    }

    @GetMapping("/edit-author")
    public String editAuthorPage(@RequestParam("id") String id, Model model) {
        Optional<AuthorDto> author = authorService.getById(id);
        model.addAttribute("author", author
                .map(authorDto -> mappingService.map(authorDto, Author.class))
                .orElseThrow(() -> new AuthorDoesNotExistException("Author with id=" + id + " has not found")));
        return "author-edit";
    }

    @GetMapping("/create-author")
    public String createAuthorPage(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "author-create";
    }

    @PostMapping("/edit-author")
    public String editAuthor(Author author, Model model) {
        authorService.save(author);
        model.addAttribute("author", author);
        return "redirect:/author-list";
    }

    @PostMapping("/create-author")
    public String createAuthor(Author author, Model model) {
        authorService.save(author);
        model.addAttribute("author", author);
        return "redirect:/author-list";
    }

    @PostMapping("/delete-author")
    public String deleteAuthor(@RequestParam("id") String id, Model model) {
        authorService.deleteById(id);
        val authors = authorService.getAll();
        model.addAttribute("authors", authors);
        return "author-list";
    }

}
