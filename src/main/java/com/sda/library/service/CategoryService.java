package com.sda.library.service;

import com.sda.library.exception.CategoryNotFoundException;
import com.sda.library.model.Category;
import com.sda.library.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException(id)
        );
        return category;
    }

    public Category getCategoryByNameOrAlias(String nameOrAlias) {
        List<Category> category = categoryRepository.findAll();
        for (int i = 0; i < category.size(); i++) {
            if (category.get(i).getName().equals(nameOrAlias) || category.get(i).getAlias().equals(nameOrAlias)) {
                return category.get(i);
            }
        }
        return null;
    }

    @Transactional
    public Set<Category> saveUnique(Set<Category> categories) {
        Set<Category> categoryUnique = new HashSet<Category>();
        categories.forEach(c -> {
            Category category = getCategoryByNameOrAlias(c.getName());
            if (category != null){
                categoryUnique.add(category);
            } else {
                category = save(c);
                categoryUnique.add(category);
            }
        });
        return categoryUnique;
    }
}
