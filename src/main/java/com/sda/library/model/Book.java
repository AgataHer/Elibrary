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

    private String subtitle;

    private String isbn;

    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(joinColumns =
    @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "author_id",
                    referencedColumnName = "id"))
    private Set<Author> authors;

    @ManyToOne
    private Publisher publisher;

    @Column(name = "self_link")
    private String selfLink;

    @Column(name = "published_date")
    private Integer publishedDate;

    private String isbn10;

    private String isbn13;

    @Column(name = "page_count")
    private Integer pageCount;

    private String language;

    @Column(name = "cover_link")
    private String coverLink;

    @Column(name = "json_info")
    private String jsonInfo;


    public Book(String title, String subtitle, String isbn, Category category, Set<Author> authors, Publisher publisher, String selfLink, Integer publishedDate, String isbn10, String isbn13, Integer pageCount, String language, String coverLink, String jsonInfo) {
        this.title = title;
        this.subtitle = subtitle;
        this.isbn = isbn;
        this.category = category;
        this.authors = authors;
        this.publisher = publisher;
        this.selfLink = selfLink;
        this.publishedDate = publishedDate;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.pageCount = pageCount;
        this.language = language;
        this.coverLink = coverLink;
        this.jsonInfo = jsonInfo;
    }

    public Book(){
    }
}
