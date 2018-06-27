package com.sda.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sda.library.model.User;
import org.springframework.transaction.annotation.Transactional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

	@Transactional
	@Modifying
	@Query("update User u set u.name = ?1, u.lastName = ?2, u.password = ?3 where u.id = ?4")
	void setUserDataById(String name, String lastName, String password, Long userId);
}
