package com.sda.library.repository;

import com.sda.library.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    public Publisher findByName(String name);
}
