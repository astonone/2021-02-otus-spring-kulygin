package ru.otus.kulygin.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleEntityNotFound(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        e.printStackTrace();
        modelAndView.addObject("errorMsg", e.getMessage());
        return modelAndView;
    }
}
