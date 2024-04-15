package com.example.bookapi.repository;

import com.example.bookapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
