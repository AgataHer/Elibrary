package com.sda.library.service;

import com.sda.library.exception.PublisherNotFoundException;
import com.sda.library.model.Publisher;
import com.sda.library.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherService {

    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public List<Publisher> getPublishers(){
        return publisherRepository.findAll();
    }

    public Publisher save(Publisher role) {
        return publisherRepository.save(role);
    }

    public Publisher getPublisherById(Long id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(
                () -> new PublisherNotFoundException(id)
        );
        return publisher;
    }
}
