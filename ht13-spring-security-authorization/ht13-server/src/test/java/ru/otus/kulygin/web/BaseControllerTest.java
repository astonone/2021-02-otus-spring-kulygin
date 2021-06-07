package ru.otus.kulygin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.kulygin.service.*;
import ru.otus.kulygin.service.impl.mapping.MappingService;

public class BaseControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected GenreService genreService;
    @MockBean
    protected AuthorService authorService;
    @MockBean
    protected BookService bookService;
    @MockBean
    protected CommentService commentService;
    @MockBean
    protected UserService userService;
    @MockBean
    protected MappingService mappingService;

}
