package com.sda.library.service;

import com.sda.library.exception.ListOfHiresNotFoundException;
import com.sda.library.model.Book;
import com.sda.library.model.Hire;
import com.sda.library.model.User;
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
    public boolean makeReservation(Book book, User user){
        boolean MadeAReservationPositively = false;
        if (book.getStateOfBook().equals("BORROWED")|| book.getStateOfBook().equals("RESERVED")){
            Long howMuchUsersAreInQueue = hireRepository.findAll().stream()
                    .filter(h->h.getBook().equals(book))
                    .filter(h->h.getRentDate().equals(null))
                    .count();
            if (howMuchUsersAreInQueue<3) {
                Hire hire = new Hire();
                hire.setBook(book);
                hire.setUser(user);
                hire.setReservationDate(System.currentTimeMillis());

                MadeAReservationPositively = true;
            }
        }
        return MadeAReservationPositively;
    }
}
