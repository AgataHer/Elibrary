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

    private Date rentDate;

    private Date reservationDate;

    private Date returnDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Book book;

    @ManyToOne(cascade = CascadeType.ALL) //TODO: dodać drugą część relacji w klasie User
    private User user;

}