package com.sda.library.controller;

import com.sda.library.model.Hire;
import com.sda.library.service.HireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HireController {

    private HireService hireService;

    @Autowired
    public HireController(HireService hireService){
        this.hireService = hireService;
    }

    @RequestMapping(value = "/hires", method = RequestMethod.GET)
    public List<Hire> showHires() {
        return hireService.getHires();
    }

    @RequestMapping(value = "/hire", method = RequestMethod.POST)
    public Hire addHire(
            @RequestBody Hire hire) {
        return hireService.save(hire);
    }

    @RequestMapping(value = "/hire/{id}", method = RequestMethod.GET)
    public ResponseEntity<Hire> getPublisherId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(hireService.getHireById(id));
    }
}
