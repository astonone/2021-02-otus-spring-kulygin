package ru.otus.kulygin.web;

import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.kulygin.domain.Genre;
import ru.otus.kulygin.dto.GenreDto;
import ru.otus.kulygin.exception.GenreDoesNotExistException;
import ru.otus.kulygin.service.GenreService;
import ru.otus.kulygin.service.impl.mapping.MappingService;

@Controller
public class GenreController {

    private final GenreService genreService;
    private final MappingService mappingService;

    public GenreController(GenreService genreService, MappingService mappingService) {
        this.genreService = genreService;
        this.mappingService = mappingService;
    }

    @GetMapping("/genre-list")
    public String listGenres(Model model) {
        val genres = genreService.getAll();
        model.addAttribute("genres", genres);
        return "genre-list";
    }

    @GetMapping("/edit-genre")
    public String editGenrePage(@RequestParam("id") String id, Model model) {
        val genre = genreService.getById(id);
        model.addAttribute("genre", genre
                .orElseThrow(() -> new GenreDoesNotExistException("Genre with id=" + id + " has not found")));
        return "genre-edit";
    }

    @GetMapping("/create-genre")
    public String createGenrePage(Model model) {
        val genre = new Genre();
        model.addAttribute("genre", genre);
        return "genre-create";
    }

    @PostMapping("/edit-genre")
    public String editGenre(GenreDto genre) {
        genreService.save(mappingService.map(genre, Genre.class));
        return "redirect:/genre-list";
    }

    @PostMapping("/create-genre")
    public String createGenre(GenreDto genre) {
        genreService.save(mappingService.map(genre, Genre.class));
        return "redirect:/genre-list";
    }

    @PostMapping("/delete-genre")
    public String deleteGenre(@RequestParam("id") String id) {
        genreService.deleteById(id);
        return "redirect:/genre-list";
    }

}
