package com.sda.library.repository;

import com.sda.library.model.Hire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("hireRepository")
public interface HireRepository extends JpaRepository<Hire, Long> {

}
