package com.sda.library.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "publishers")
public class Publisher {

    /**
     * This class determine data types storing in table publishers, including publishers' name & address
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;

    @OneToMany(mappedBy = "publisher")
    private Set<Book> book;

    public Publisher(Long id, String name, String address, Set<Book> book) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.book = book;
    }

    public Publisher() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Book> giveBook() {
        return book;
    }

    public void setBook(Set<Book> book) {
        this.book = book;
    }
}
