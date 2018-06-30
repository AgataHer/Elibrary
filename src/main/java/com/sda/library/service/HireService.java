package com.sda.library.service;

import com.sda.library.exception.ListOfHiresNotFoundException;
import com.sda.library.model.Book;
import com.sda.library.model.Hire;
import com.sda.library.model.StateOfBook;
import com.sda.library.model.User;
import com.sda.library.repository.HireRepository;
import com.sda.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HireService {

    private HireRepository hireRepository;
    private UserRepository userRepository;

    @Autowired
    public HireService(HireRepository hireRepository, UserRepository userRepository) {
        this.hireRepository = hireRepository;
        this.userRepository = userRepository;
    }

    public List<Hire> getHires(){
        return hireRepository.findAll();
    }

    public Hire save(Hire hire, Book book, Long userId) {
        book.setStateOfBook(StateOfBook.BORROWED);
        hire.setReservationDate(null);
        Long creationTime = System.currentTimeMillis();
        hire.setRentDate(creationTime);
        hire.setReturnDate(creationTime+30L);
        hire.setUser(userRepository.findById(userId).get());
        hire.setBook(book);
        return hireRepository.save(hire);
    }

    public Hire getHireById(Long id) {
        Hire hire = hireRepository.findById(id).orElseThrow(
                () -> new ListOfHiresNotFoundException(id)
        );
        return hire;
    }
//    public boolean makeReservation(Book book, User user){
//        boolean MadeAReservationPositively = false;
//        if (book.getStateOfBook().equals("BORROWED")|| book.getStateOfBook().equals("RESERVED")){
//            Long howMuchUsersAreInQueue = hireRepository.findAll().stream()
//                    .filter(h->h.getBook().equals(book))
//                    .filter(h->h.getRentDate().equals(null))
//                    .count();
//            if (howMuchUsersAreInQueue<3) {
//                Hire hire = new Hire();
//                hire.setBook(book);
//                hire.setUser(user);
//                hire.setReservationDate(System.currentTimeMillis());
//
//                MadeAReservationPositively = true;
//            }
//        }
////        return MadeAReservationPositively;
//    }
}
