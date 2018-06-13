package com.sda.library.DTO;

import com.sda.library.model.TypesOfUsers;
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
    private TypesOfUsers typesOfUsers; //TODO: przepisać metody z UserService aby używały UserDTO
}
