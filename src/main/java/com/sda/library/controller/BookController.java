package com.sda.library.controller;


import com.sda.library.model.Book;
import com.sda.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private BookService bookService;

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



}
