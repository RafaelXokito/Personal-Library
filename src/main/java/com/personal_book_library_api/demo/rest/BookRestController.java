package com.personal_book_library_api.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal_book_library_api.demo.services.BookService;

@RestController
@RequestMapping("/api")
public class BookRestController {
    
    private BookService bookService;

    public BookRestController() {
    }

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public String findAll() {
        System.out.println("findAll() called");
        return bookService.findAll().toString();
    }

}
