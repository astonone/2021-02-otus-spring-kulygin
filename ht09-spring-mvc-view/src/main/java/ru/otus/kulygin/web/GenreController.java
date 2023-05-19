package ru.otus.kulygin.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.exception.GenreDoesNotExistException;
import ru.otus.kulygin.service.GenreService;

@Controller
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genre-list")
    public String listGenres(Model model) {
        model.addAttribute("genres", genreService.getAll());
        return "genre-list";
    }

    @GetMapping("/edit-genre")
    public String editGenrePage(@RequestParam("id") String id, Model model) {
        model.addAttribute("genre", genreService.getById(id)
                .orElseThrow(() -> new GenreDoesNotExistException("Genre with id=" + id + " has not found")));
        return "genre-edit";
    }

    @GetMapping("/create-genre")
    public String createGenrePage(Model model) {
        model.addAttribute("genre", new Genre());
        return "genre-create";
    }

    @PostMapping("/edit-genre")
    public String editGenre(GenreDto genre) {
        genreService.save(genre);
        return "redirect:/genre-list";
    }

    @PostMapping("/create-genre")
    public String createGenre(GenreDto genre) {
        genreService.save(genre);
        return "redirect:/genre-list";
    }

    @PostMapping("/delete-genre")
    public String deleteGenre(@RequestParam("id") String id) {
        genreService.deleteById(id);
        return "redirect:/genre-list";
    }

}
