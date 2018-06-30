package com.sda.library.service;

import com.sda.library.exception.AuthorNotFoundException;
import com.sda.library.model.Author;
import com.sda.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(final AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public Author getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(
                () -> new AuthorNotFoundException(id)
        );
        return author;
    }

    public Author getAuthorByFullname(String fullname) {
        List<Author> authors = authorRepository.findAll();
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getFullname().equals(fullname)) {
                return authors.get(i);
            }
        }
        return null;
    }

    @Transactional
    public Set<Author> saveUnique(Set<Author> authors) {
        Set<Author> authorsUnique = new HashSet<Author>();
        authors.forEach(a -> {
            Author author = getAuthorByFullname(a.getFullname());
            if (author != null){
                authorsUnique.add(author);
            } else {
                author = save(author);
                authorsUnique.add(author);
            }
        });
        return authorsUnique;
    }
}
