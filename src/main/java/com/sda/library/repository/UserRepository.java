package com.sda.library.repository;

import com.sda.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //public User findById (Long id);
    //public User findByName (String name, String lastname);

}
