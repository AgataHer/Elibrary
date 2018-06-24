package com.sda.library.dto;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String name;
    private String lastname;
    private String mail;
    private String address;
    private String postalcode;
    private String phone;
    private int role;
}
