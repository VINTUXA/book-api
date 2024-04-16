package com.example.bookapi.controller;

import com.example.bookapi.entity.Book;
import com.example.bookapi.entity.UpsertBookRequest;
import com.example.bookapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/book")
@RequiredArgsConstructor
public class BookController {
    @Autowired
    private final BookService bookService;

//    найти одну книгу по её названию и автору,
//    найти список книг по имени категории,
//    создать книгу,
//    обновить информацию о книге,
//    удалить книгу по ID.

    @GetMapping
    public ResponseEntity<List<Book>> getAll(){
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/by-title-and-author")
    public ResponseEntity<Book> getBookByTitleAndAuthor(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "author") String author
    ){
        return ResponseEntity.ok(bookService.getBookByTitleAndAuthor(title, author));
    }

    @GetMapping("/by-category-name")
    public ResponseEntity<List<Book>> getBooksByCategoryName(
            @RequestParam(value = "category") String categoryName
    ){
        return ResponseEntity.ok(bookService.getBooksByCategoryName(categoryName));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody UpsertBookRequest request){
        System.out.println(request.getTitle());
        System.out.println("dsfd");
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @RequestBody UpsertBookRequest request
    ){
        System.out.println(request.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById( @PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
