package com.personal_book_library_api.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal_book_library_api.demo.dtos.BookRepository;
import com.personal_book_library_api.demo.entities.Book;

@Service
public class BookService {
    
    private BookRepository bookRepository;

    public BookService() {
    }

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

}
