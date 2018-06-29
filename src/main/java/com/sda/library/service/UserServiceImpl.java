package com.sda.library.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sda.library.model.Role;
import com.sda.library.model.User;
import com.sda.library.repository.RoleRepository;
import com.sda.library.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        List<User> usersCount = userRepository.findAll();
		Role userRole;
        if (usersCount.size() == 0) {
			userRole = roleRepository.findByRole("ADMIN");
		} else {
			userRole = roleRepository.findByRole("CLIENT");
		}
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}

	@Override
	public void updateUserData(User user) {
		user.setId(userRepository.findByEmail(user.getEmail()).getId());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.setUserDataById(user.getName(),user.getLastName(), user.getPassword(), user.getId());
	}
}
