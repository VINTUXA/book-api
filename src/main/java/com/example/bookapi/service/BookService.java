package com.example.bookapi.service;

import com.example.bookapi.entity.Book;
import com.example.bookapi.entity.Category;
import com.example.bookapi.entity.UpsertBookRequest;
import com.example.bookapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final CategoryService categoryService;

//    найти одну книгу по её названию и автору,
//    найти список книг по имени категории,
//    создать книгу,
//    обновить информацию о книге,
//    удалить книгу по ID.
    public List<Book> findAll(){
        return repository.findAll();
    }

    public Book getBookByTitleAndAuthor(String title, String author){
        return new Book(43l,title,author,new Category(1l,"category"));
    }

    public List<Book> getBooksByCategoryName(String categoryName){
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(43l,"title","author",new Category(1l,"category")));
        return bookList;
    }

    public Book saveBook(UpsertBookRequest request){
        Book newBook = new Book();
        Category category = categoryService.createCategory(request.getCategoryName());
        newBook.setCategory(category);
        newBook.setAuthor(request.getAuthor());
        newBook.setTitle(request.getTitle());
//        return new Book(43l,request.getTitle(),request.getAuthor(),new Category(1l,request.getCategoryName()));
        return repository.save(newBook);
    }

    public Book findById(Long id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException(MessageFormat.format("Book with id: {} not found", id)));
    }

    public Book updateBook(Long id, UpsertBookRequest request){
        Book existedBook = findById(id);
        existedBook.setTitle(request.getTitle());
        existedBook.setAuthor(request.getAuthor());
        existedBook.setCategory(categoryService.createCategory(request.getCategoryName()));
        return repository.save(existedBook);
//        return new Book(43l,request.getTitle(),request.getAuthor(),new Category(1l,request.getCategoryName()));
    }

    public void deleteById(Long id){
        return;
    }
}
