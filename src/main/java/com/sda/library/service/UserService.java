package com.sda.library.service;

import com.sda.library.model.User;

import java.util.Optional;

public interface UserService {
	 User findUserByEmail(String email);
	 void saveUser(User user);
	 Optional<User> findUserById(Long id);
	 void updateUserData(User user);
}
