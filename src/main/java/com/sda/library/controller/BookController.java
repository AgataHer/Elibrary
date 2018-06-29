package com.sda.library.controller;


import com.sda.library.model.Author;
import com.sda.library.model.Book;
import com.sda.library.model.Category;
import com.sda.library.model.User;
import com.sda.library.service.BookService;
import com.sda.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
    public ModelAndView addBookRedirect(){
        ModelAndView modelAndView = new ModelAndView();
        Book book = new Book();
        Category category = new Category();
        Author author = new Author();
//        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
//        modelAndView.addObject("userMessage","Content Available Only for Users");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("book", book);
        modelAndView.addObject("category", category);
        modelAndView.addObject("author", author);
        modelAndView.setViewName("elibrary/add_book");
        return modelAndView;
    }

    @RequestMapping(value="/add_book", method = RequestMethod.POST)
    public ModelAndView addNewBook(@Valid Book book, Category category, Author author, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
/*
        TODO teraz odczyt z parametrów
        category findbyname
                author findbyname
                        publisher findbyname
                                złożenie book do kupy
*/
        if (!book.getIsbn13().equals("") && book.getTitle().equals("") && book.getSubtitle().equals("")) {
            book = BookService.getBookByIsbnFromWeb(book.getIsbn13());
            modelAndView.addObject("book", book);
            modelAndView.setViewName("add_book");
        }
        modelAndView.setViewName("elibrary/add_book");
        return modelAndView;
    }

    @RequestMapping(value="/remove_book/{id}", method = RequestMethod.GET)
    public ModelAndView deleteBook(@PathVariable("id") Long id){
        bookService.delete(bookService.getBookById(id));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("elibrary/index");
        return modelAndView;
    }

    @RequestMapping(value="/find_book_via_isbn", method = RequestMethod.POST)
    public ModelAndView addNewBook(@Valid Book book, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
/*
        TODO teraz odczyt z parametrów
        category findbyname
                author findbyname
                        publisher findbyname
                                złożenie book do kupy
*/
        modelAndView.setViewName("elibrary/index");
        return modelAndView;
    }

}
