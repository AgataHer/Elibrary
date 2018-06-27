package com.sda.library.controller;


import com.sda.library.model.Book;
import com.sda.library.model.User;
import com.sda.library.service.BookService;
import com.sda.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class BookController {

    private BookService bookService;
    private UserService userService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(value = "/books"/*, method = RequestMethod.GET*/)
    public List<Book> showBooks() {
        return bookService.getBooks();
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public Book addBook(
            @RequestBody Book book) {
        return bookService.save(book);
    }

    @RequestMapping(value = "/book/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBookId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookService.getBookById(id));
    }

    @RequestMapping(value="/add_book", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = userService.findUserByEmail(auth.getName());
        Book book = new Book();
//        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
//        modelAndView.addObject("userMessage","Content Available Only for Users");
//        modelAndView.addObject("auth", auth);
        modelAndView.addObject("book", book);
        modelAndView.setViewName("elibrary/add_book");
        return modelAndView;
    }


}
