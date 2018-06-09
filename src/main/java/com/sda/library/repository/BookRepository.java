package com.sda.library.repository;

import com.sda.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    public Book findByTitle(String title);
}
