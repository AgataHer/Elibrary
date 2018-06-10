package com.sda.library.service;

import com.sda.library.exception.UserNotFoundException;
import com.sda.library.model.User;
import com.sda.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService (UserRepository userRepository) {this.userRepository = userRepository;}

    public List<User> getUsers() {return userRepository.findAll();}

    public User savae(User user) {return userRepository.save(user);}

    public User getUserById (Long id){

        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException(id)
        );
        return user;
    }

}
