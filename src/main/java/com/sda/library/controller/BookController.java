package com.sda.library.controller;


import com.sda.library.model.*;
import com.sda.library.service.*;
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
import java.util.Set;

@RestController
public class BookController {

    private BookService bookService;
    private UserService userService;
    private HireService hireService;


    @Autowired
    public BookController(BookService bookService, UserService userService, PublisherService publisherService,
                          CategoryService categoryService, AuthorService authorService, HireService hireService) {
        this.bookService = bookService;
        this.userService = userService;
        this.hireService = hireService;
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
    public ModelAndView addNewBook(@Valid Book book, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        if (!book.getIsbn13().equals("") && book.getTitle().equals("") && book.getSubtitle().equals("")) {
            book = BookService.getBookByIsbnFromWeb(book.getIsbn13());
            modelAndView.addObject("book", book);
            book = bookService.saveComplex(book);
            modelAndView.setViewName("elibrary/index");
        } else {
            book.setStateOfBook(StateOfBook.FOR_BORROW);
            bookService.save(book);
            modelAndView.setViewName("elibrary/index");
        }
        return modelAndView;
    }

    @RequestMapping(value="/remove_book/{id}", method = RequestMethod.GET)
    public ModelAndView deleteBook(@PathVariable("id") Long id){
        bookService.delete(bookService.getBookById(id));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("elibrary/index");
        return modelAndView;
    }
    @RequestMapping(value="/reserve_book/{id}", method = RequestMethod.GET)
    public ModelAndView reservedBook(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        Book book = bookService.getBookById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        bookService.updateBookData(book);
        hireService.makeReservation(book, user);
        modelAndView.setViewName("elibrary/index");
        return modelAndView;
    }
}
