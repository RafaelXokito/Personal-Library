package com.personal_book_library_api.demo.services;

import java.util.List;

import com.personal_book_library_api.demo.dtos.WriterRepository;
import com.personal_book_library_api.demo.entities.Writer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal_book_library_api.demo.dtos.BookRepository;
import com.personal_book_library_api.demo.entities.Book;

@Service
public class BookService {
    
    private BookRepository bookRepository;
    private WriterRepository writerRepository;

    public BookService() {
    }

    @Autowired
    public BookService(BookRepository bookRepository, WriterRepository writerRepository) {
        this.bookRepository = bookRepository;
        this.writerRepository = writerRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public void save(Book book, String email) {
        Writer writer = writerRepository.findByEmail(email).orElse(null);
        System.out.println(writer);
        book.setWriter(writer);
        bookRepository.save(book);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }
}
