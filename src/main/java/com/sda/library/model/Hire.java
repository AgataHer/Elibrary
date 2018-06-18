package com.sda.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

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
    private Long rentDate;

    @Column(name = "reservation_date")
    private Long reservationDate;

    @Column(name = "return_date")
    private Long returnDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Book book;

    @ManyToOne(cascade = CascadeType.ALL) //TODO: dodać drugą część relacji w klasie User
    private User user;

}