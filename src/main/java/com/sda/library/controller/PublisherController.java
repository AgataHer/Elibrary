package com.sda.library.controller;

import com.sda.library.model.Publisher;
import com.sda.library.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @RequestMapping(value = "/publishers", method = RequestMethod.GET)
    public List<Publisher> showPublishers() {
        return publisherService.getPublishers();
    }

    @RequestMapping(value = "/publisher", method = RequestMethod.POST)
    public Publisher addPublisher(
            @RequestBody Publisher publisher) {
        return publisherService.save(publisher);
    }

    @RequestMapping(value = "/publisher/{id}", method = RequestMethod.GET)
    public ResponseEntity<Publisher> getPublisherId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(publisherService.getPublisherById(id));
    }
}
