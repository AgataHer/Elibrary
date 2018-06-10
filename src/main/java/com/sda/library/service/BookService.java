package com.sda.library.service;

import com.sda.library.exception.BookNotFoundException;
import com.sda.library.model.Book;
import com.sda.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(Long id) {

        Book book = bookRepository.findById(id).orElseThrow(
                () -> new BookNotFoundException(id)
        );
        return book;
    }
}
