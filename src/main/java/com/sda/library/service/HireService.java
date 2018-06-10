package com.sda.library.service;

import com.sda.library.exception.ListOfHiresNotFoundException;
import com.sda.library.model.Hire;
import com.sda.library.repository.HireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HireService {
    private HireRepository hireRepository;

    @Autowired
    public HireService(HireRepository hireRepository) {
        this.hireRepository = hireRepository;
    }

    public List<Hire> getHires(){
        return hireRepository.findAll();
    }

    public Hire save(Hire hire) {
        return hireRepository.save(hire);
    }

    public Hire getHireById(Long id) {
        Hire hire = hireRepository.findById(id).orElseThrow(
                () -> new ListOfHiresNotFoundException(id)
        );
        return hire;
    }
}
