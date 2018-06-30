package com.sda.library.repository;

import com.sda.library.model.Book;
import com.sda.library.model.StateOfBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Transactional
    @Modifying
    @Query("update Book b set b.stateOfBook = ?1 where b.id = ?2")
    void setBookDataById(StateOfBook stateOfBook, Long id);
}
