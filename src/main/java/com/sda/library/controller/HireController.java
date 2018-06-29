package com.sda.library.controller;

import com.sda.library.model.Book;
import com.sda.library.model.Hire;
import com.sda.library.model.User;
import com.sda.library.service.BookService;
import com.sda.library.service.HireService;
import com.sda.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
public class HireController {

    private HireService hireService;
    private BookService bookService;
    private UserService userService;

    @Autowired
    public HireController(HireService hireService, BookService bookService, UserService userService){
        this.hireService = hireService;
        this.bookService = bookService;
        this.userService = userService;
    }

//    @RequestMapping(value = "/hires", method = RequestMethod.GET)
//    public List<Hire> showHires() {
//        return hireService.getHires();
//    }
//
//    @RequestMapping(value = "/hire", method = RequestMethod.POST)
//    public Hire addHire(
//            @RequestBody Hire hire) {
//        return hireService.save(hire);
//    }
//
//    @RequestMapping(value = "/hire/{id}", method = RequestMethod.GET)
//    public ResponseEntity<Hire> getPublisherId(@PathVariable Long id) {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(hireService.getHireById(id));
//    }
    @RequestMapping(value="/borrow_book/{id}", method = RequestMethod.GET)
    public ModelAndView methodToViewHireForm(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        Book book = bookService.getBookById(id);
            modelAndView.addObject("book", book);
            User user = new User();
            modelAndView.addObject("user", user);
        modelAndView.setViewName("elibrary/borrowBook");
        return modelAndView;
    }
//    @RequestMapping(value = "/borrow_book/{id}", method = RequestMethod.POST)
//    public ModelAndView methodToGetBookIdAndNextCreateBookHire(@PathVariable("id") Long id, @Valid User user, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView();
//        if (bindingResult.hasErrors()) {
//            modelAndView.setViewName("elibrary/index");
//        } else {
//            Book book = bookService.getBookById(id);
//            Hire hire = new Hire();
//            hireService.save(hire, book, user);
//            modelAndView.addObject("successMessage", "Książka została wypożyczona  !");
//            modelAndView.addObject("hire", new Hire());
//            modelAndView.setViewName("elibrary/borrowBook");
//        }
//        return modelAndView;
//    }
}
