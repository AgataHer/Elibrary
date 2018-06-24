package com.sda.library.service;

import com.sda.library.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
