package com.sda.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor

@Data
@Entity
@Table(name = "hire")
public class Hire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "rent_date")
    private Date rentDate;

    @Column(name = "reservation_date")
    private Date reservationDate;

    @Column(name = "return_date")
    private Date returnDate;

    @ManyToOne(cascade = CascadeType.ALL) //TODO: dodać drugą część relacji w klasie Book
    private Book book;

    @ManyToOne(cascade = CascadeType.ALL) //TODO: dodać drugą część relacji w klasie User
    private User user;

}