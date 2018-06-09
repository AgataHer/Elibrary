package com.sda.library.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String isbn;

    @ManyToOne(cascade = CascadeType.ALL)
//    @ManyToOne(cascade = CascadeType.REMOVE) //MERGE, ALL(?), DETACH, PERSIST(?), REFRESH
    private Category category;

    @ManyToMany(cascade = CascadeType.ALL)
//    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(joinColumns =
    @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "author_id",
                    referencedColumnName = "id"))
    private Set<Author> authors;

    @ManyToOne
    private Publisher publisher;

    public Book(String title, String isbn, Set<Author> authors, Category category, Publisher publisher) {
        this.title = title;
        this.isbn = isbn;
        this.authors = authors;
        this.category = category;
        this.publisher = publisher;
    }

    public Book(){}
}
