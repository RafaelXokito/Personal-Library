package com.personal_book_library_api.demo.rest;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.personal_book_library_api.demo.dtos.*;
import com.personal_book_library_api.demo.entities.Book;
import com.personal_book_library_api.demo.entities.ReaderBook;
import com.personal_book_library_api.demo.entities.Writer;
import com.personal_book_library_api.demo.services.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.personal_book_library_api.demo.services.BookService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookRestController {
    
    private BookService bookService;
    private WriterService writerService;

    public BookRestController() {
    }

    @Autowired
    public BookRestController(BookService bookService, WriterService writerService) {
        this.bookService = bookService;
        this.writerService = writerService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookSimpleDTO>> findAll() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(BookSimpleDTO.from(books));
    }

    @GetMapping("/books/search")
    public ResponseEntity<List<BookSimpleDTO>> search(@RequestParam(required = false, defaultValue = "") String title, @RequestParam(required = false, defaultValue = "") String keyword, @RequestParam(required = false, defaultValue = "") String writerName) {
        List<Book> books = bookService.search(title, keyword, writerName);
        return ResponseEntity.ok(BookSimpleDTO.from(books));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookSingleDTO> find(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(BookSingleDTO.from(book));
    }

    @PostMapping("/books")
    public ResponseEntity<BookDTO> save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Book book) {
        // userDetails.getUsername() It returns the email from the authenticated user
        bookService.save(book, userDetails.getUsername());
        return ResponseEntity.ok(BookDTO.from(book));
    }

    @PatchMapping("/books/{id}/add")
    public ResponseEntity<ReaderBookDTO> addBook(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){
        ReaderBook readerBook = bookService.addBook(userDetails.getUsername(), id);
        return ResponseEntity.ok(ReaderBookDTO.from(readerBook));
    }

    @DeleteMapping("/books/{id}/remove")
    public ResponseEntity<TypeResolutionContext.Empty> removeBook(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){
        bookService.removeBook(userDetails.getUsername(), id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/books/{bookId}/read")
    public ResponseEntity<PageDTO> readBook(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long bookId){
        PageDTO pageDTO = bookService.readBook(userDetails.getUsername(), bookId);
        return ResponseEntity.ok(pageDTO);
    }

    @PatchMapping("/books/nextpage")
    public ResponseEntity<PageDTO> nextPage(@AuthenticationPrincipal UserDetails userDetails){
        PageDTO pageDTO = bookService.nextPage(userDetails.getUsername());
        return ResponseEntity.ok(pageDTO);
    }

    @PatchMapping("/books/previouspage")
    public ResponseEntity<PageDTO> previousPage(@AuthenticationPrincipal UserDetails userDetails){
        PageDTO pageDTO = bookService.previousPage(userDetails.getUsername());
        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping("/books/{id}/readers")
    public ResponseEntity<List<ReaderSimpleDTO>> getReaders(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Writer writer = writerService.getWriter(userDetails.getUsername());
        Book book = bookService.findById(id);
        if (!book.getWriter().equals(writer)) {
            throw new RuntimeException("You are not the writer of this book");
        }
        return ResponseEntity.ok(ReaderSimpleDTO.fromReaderBooks(book.getReaders()));
    }

    @GetMapping("/books/{id}/currentreaders")
    public ResponseEntity<List<ReaderSimpleDTO>> getCurrentReaders(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Writer writer = writerService.getWriter(userDetails.getUsername());
        Book book = bookService.findById(id);
        if (!book.getWriter().equals(writer)) {
            throw new RuntimeException("You are not the writer of this book");
        }
        return ResponseEntity.ok(ReaderSimpleDTO.from(book.getCurrentReaders()));
    }

    @GetMapping("/books/{id}/writer")
    public ResponseEntity<WriterSimpleDTO> getWriter(@PathVariable Long id) {
        Writer writer = bookService.getWriter(id);
        return ResponseEntity.ok(WriterSimpleDTO.from(writer));
    }
}
