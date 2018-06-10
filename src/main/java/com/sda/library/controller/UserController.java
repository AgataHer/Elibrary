package com.sda.library.controller;


import com.sda.library.model.User;
import com.sda.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> showUsers() {
        return userService.getUsers();
    }

    public User addUser(
            @RequestBody User user) {
        return userService.savae(user);
    }

    public ResponseEntity<User> getUserId (@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUserById(id));
    }
}