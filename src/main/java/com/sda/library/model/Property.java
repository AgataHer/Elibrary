package com.sda.library.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor

@Setter
@Getter

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @Column(unique = true, nullable = false)
    private String keyName;

    @Column(name = "value_str")
    private String valueStr;

    @Column(name = "value_int")
    private Integer valueInt;

    @Column(name = "value_dbl")
    private Double valueDbl;

    @Column(name = "value_bool")
    private Boolean valueBool;
}
