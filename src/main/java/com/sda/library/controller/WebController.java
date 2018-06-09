package com.sda.library.controller;

import com.sda.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;

    @GetMapping(value = "/index")
    public ModelAndView index(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(value = "/web/authors")
    public ModelAndView authors(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("authorsList", authorService.getAuthors());
        modelAndView.setViewName("authors");
        return modelAndView;
    }

//    @CrossOrigin(value = "http://localhost:3000, http://192.168.0.22:8080")
    @GetMapping(value = "/web/books")
    public ModelAndView books(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("booksList", bookService.getBooks());
        modelAndView.setViewName("books");
        return modelAndView;
    }
}
