package com.sda.library.controller;

import com.sda.library.model.Category;
import com.sda.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {


    @Autowired
    private CategoryService categoryService;


    @RequestMapping(value = "/categories")
    public List<Category> showCategories() {
        return categoryService.getCategories();
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public Category addCategory(
            @RequestBody Category category) {
        return categoryService.save(category);
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    public ResponseEntity<Category> getCategoryId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getCategoryById(id));
    }


}
