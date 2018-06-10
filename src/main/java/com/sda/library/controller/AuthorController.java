package com.sda.library.controller;

import com.sda.library.model.Author;
import com.sda.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {

    private AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public List<Author> showAuthors() {
        return authorService.getAuthors();
    }

    @RequestMapping(value = "/author", method = RequestMethod.POST)
    public Author addAuthor(
            @RequestBody Author author) {
        return authorService.save(author);
    }

    @RequestMapping(value = "/author/{id}", method = RequestMethod.GET)
    public ResponseEntity<Author> getAuthorId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authorService.getAuthorById(id));
    }
}
