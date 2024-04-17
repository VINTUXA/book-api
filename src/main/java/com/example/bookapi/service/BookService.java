package com.example.bookapi.service;

import com.example.bookapi.entity.Book;
import com.example.bookapi.entity.Category;
import com.example.bookapi.entity.UpsertBookRequest;
import com.example.bookapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final CategoryService categoryService;
    private static final Logger logger = Logger.getLogger(BookService.class.getName());
    public List<Book> findAll(){
        return repository.findAll();
    }

    @Cacheable(value = "booksByTitleAndAuthor", key = "#title.concat('-').concat(#author)")
    public Book getBookByTitleAndAuthor(String title, String author){
        logger.info("Database request by title and author for title: " + title + ", author: " + author);
        Book book = repository.findFirstByTitleAndAuthor(title, author);
        return book;
    }

    @Cacheable(value = "booksByCategory", key = "#categoryName")
    public List<Book> getBooksByCategoryName(String categoryName){
        logger.info("Database request by category for category: " + categoryName);
        List<Book> bookList = repository.findByCategoryName(categoryName);
        return bookList;
    }

    @Caching(evict = {
            @CacheEvict(value = "booksByTitleAndAuthor", allEntries = true),
            @CacheEvict(value = "booksByCategory", allEntries = true)
    })
    public Book saveBook(UpsertBookRequest request){
        logger.info("Save book");
        Book newBook = new Book();
        Category category = categoryService.createCategory(request.getCategoryName());
        newBook.setCategory(category);
        newBook.setAuthor(request.getAuthor());
        newBook.setTitle(request.getTitle());
        return repository.save(newBook);
    }

    public Book findById(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Book with id: {} not found", id)));
    }

    @Caching(evict = {
            @CacheEvict(value = "booksByTitleAndAuthor", allEntries = true),
            @CacheEvict(value = "booksByCategory", allEntries = true)
    })
    public Book updateBook(Long id, UpsertBookRequest request){
        logger.info("Update book by Id");
        Book existedBook = findById(id);
        existedBook.setTitle(request.getTitle());
        existedBook.setAuthor(request.getAuthor());
        existedBook.setCategory(categoryService.createCategory(request.getCategoryName()));
        return repository.save(existedBook);
    }

    @Caching(evict = {
            @CacheEvict(value = "booksByTitleAndAuthor", allEntries = true),
            @CacheEvict(value = "booksByCategory", allEntries = true)
    })
    public void deleteById(Long id){
        logger.info("Delete book by Id");
        repository.deleteById(id);
    }
}
