package com.sda.library.service;

import com.sda.library.exception.PublisherNotFoundException;
import com.sda.library.model.Publisher;
import com.sda.library.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public Publisher save(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Transactional
    public Publisher saveUnique(String name) {
        Publisher publisherUnique = getPublisherByName(name);
        if (publisherUnique != null){
            return publisherUnique;
        } else {
            publisherUnique = new Publisher();
            publisherUnique.setName(name);
            publisherUnique = save(publisherUnique);
            return publisherUnique;
        }
    }

    public Publisher getPublisherById(Long id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(
                () -> new PublisherNotFoundException(id)
        );
        return publisher;
    }

    public Publisher getPublisherByName(String name) {
        List<Publisher> publishers = publisherRepository.findAll();
        for (int i = 0; i < publishers.size(); i++) {
            if (publishers.get(i).getName().equals(name)) {
                return publishers.get(i);
            }
        }
        return null;
    }
}
