package com.example.bookapi.service;

import com.example.bookapi.entity.Category;
import com.example.bookapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;

    public Category createCategory(String name){
        Category category = new Category();
        category.setName(name);
        return repository.save(category);
    }

}
