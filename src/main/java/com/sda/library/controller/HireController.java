package com.sda.library.controller;

import com.sda.library.model.Book;
import com.sda.library.model.Hire;
import com.sda.library.model.User;
import com.sda.library.service.BookService;
import com.sda.library.service.HireService;
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

    @Autowired
    public HireController(HireService hireService, BookService bookService){
        this.hireService = hireService;
        this.bookService = bookService;
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
    @RequestMapping(value="/borrow/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView methodToViewHireForm(@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView();
        Book book = bookService.getBookById(id);
            modelAndView.addObject("book", book);
        modelAndView.setViewName("borrow");
        return modelAndView;
    }


//    @RequestMapping(value="/borrow/{id}", method = RequestMethod.GET)
//    public ModelAndView createBookHire(){
//        ModelAndView modelAndView = new ModelAndView();
//        Hire hire= new Hire();
//            modelAndView.addObject("hire", hire);
//        modelAndView.setViewName("borrow");
//        return modelAndView;
//    }

    @RequestMapping(value = "/borrow/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView methodToGetBookIdAndNextCreateBookHire(@PathVariable("id") Long id, @Valid Hire hire, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("borrow");
        } else {
            hireService.save(hire);
            modelAndView.addObject("successMessage", "Książka została wypożyczona  !");
            modelAndView.addObject("hire", new Hire());
            modelAndView.setViewName("borrow");
        }
        return modelAndView;
    }
}
