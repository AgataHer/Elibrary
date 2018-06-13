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

    /**
     * This class determine data types storing in table books, including most important book data occurring in JSON
     * package from external APIs
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String subtitle;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(joinColumns =
    @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "category_id",
                    referencedColumnName = "id"))
    private Set<Category> categories;

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

    // kompletny json pozyskany z publicznego API
    @Column(name = "json_info")
    private String jsonInfo;

    private String description;


    public Book(String title, String subtitle, Set<Category> categories, Set<Author> authors, Publisher publisher,
                String selfLink, Integer publishedDate, String isbn10, String isbn13, Integer pageCount, String language,
                String coverLink, String jsonInfo, String description) {
        this.title = title;
        this.subtitle = subtitle;
        this.categories = categories;
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
        this.description = description;
    }

    public Book(){
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public Integer getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Integer publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCoverLink() {
        return coverLink;
    }

    public void setCoverLink(String coverLink) {
        this.coverLink = coverLink;
    }

    public String getJsonInfo() {
        return jsonInfo;
    }

    public void setJsonInfo(String jsonInfo) {
        this.jsonInfo = jsonInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
