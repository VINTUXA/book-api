package com.example.bookapi.repository;

import com.example.bookapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByCategoryName(String categoryName);
    Book findFirstByTitleAndAuthor(String title, String author);
}
