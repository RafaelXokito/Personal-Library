package com.personal_book_library_api.demo.rest;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.personal_book_library_api.demo.dtos.*;
import com.personal_book_library_api.demo.entities.Book;
import com.personal_book_library_api.demo.entities.Reader;
import com.personal_book_library_api.demo.entities.ReaderBook;
import com.personal_book_library_api.demo.entities.Writer;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.personal_book_library_api.demo.services.BookService;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<BookDTO>> findAll() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(convertToDTOList(books));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookDTO> find(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(convertToDTO(book));
    }

    @PostMapping("/books")
    public ResponseEntity<BookDTO> save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Book book) {
        // userDetails.getUsername() It returns the email from the authenticated user
        bookService.save(book, userDetails.getUsername());
        BookDTO bookDTO = convertToDTO(book);
        return ResponseEntity.ok(bookDTO);
    }

    @PatchMapping("/books/{id}/add")
    public ResponseEntity<ReaderBookDTO> addBook(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){
        ReaderBook readerBook = bookService.addBook(userDetails.getUsername(), id);
        return ResponseEntity.ok(convertReaderBookToDTOSimple(readerBook));
    }

    @DeleteMapping("/books/{id}/remove")
    public ResponseEntity<ReaderBookDTO> removeBook(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){
        ReaderBook readerBook = bookService.removeBook(userDetails.getUsername(), id);
        return ResponseEntity.ok(convertReaderBookToDTOSimple(readerBook));
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

    public ReaderBookDTO convertReaderBookToReaderDTO(ReaderBook readerBook) {
        ReaderBookDTO readerBookDTO = new ReaderBookDTO();
        readerBookDTO.setId(readerBook.getId());
        readerBookDTO.setBook(convertToDTO(readerBook.getBook()));
        readerBookDTO.setReader(convertToDTOSimple(readerBook.getReader()));
        readerBookDTO.setCurrentPage(readerBook.getCurrentPage());
        return readerBookDTO;
    }

    public ReaderBookDTO convertReaderBookToDTOSimple(ReaderBook readerBook) {
        ReaderBookDTO readerBookDTO = new ReaderBookDTO();
        readerBookDTO.setId(readerBook.getId());
        readerBookDTO.setBook(convertToDTO(readerBook.getBook()));
        readerBookDTO.setCurrentPage(readerBook.getCurrentPage());
        return readerBookDTO;
    }

    public List<BookDTO> convertToDTOList(List<Book> books) {
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setContent(book.getContent());

        // Convert the list of Reader entities to ReaderDTOs for currentReaders
        if (book.getCurrentReaders() != null) {
            bookDTO.setCurrentReaders(book.getCurrentReaders().stream()
                    .map(this::convertToDTOSimple)  // Assuming you have a convertToDTO method for Reader
                    .collect(Collectors.toList()));
        }

        // Convert the list of Reader entities to ReaderDTOs for readers
        if (book.getReaders() != null) {
            bookDTO.setReaders(book.getReaders().stream()
                    .map(readerBook -> convertToDTOSimple(readerBook.getReader()))
                    .collect(Collectors.toList()));
        }

        // Convert the Writer entity to WriterDTO
        if (book.getWriter() != null) {
            bookDTO.setWriter(convertToDTOSimple(book.getWriter()));  // Assuming you have a convertToDTO method for Writer
        }

        return bookDTO;
    }

    public ReaderDTO convertToDTOSimple(Reader reader) {
        ReaderDTO readerDTO = new ReaderDTO();
        readerDTO.setId(reader.getId());
        readerDTO.setIdCard(reader.getIdCard());
        readerDTO.setFontSize(reader.getFontSize());
        readerDTO.setFirstName(reader.getFirstName());
        readerDTO.setLastName(reader.getLastName());
        readerDTO.setActive(reader.isActive());
        readerDTO.setEmail(reader.getEmail());

        return readerDTO;
    }

    public WriterDTO convertToDTOSimple(Writer writer) {
        WriterDTO writerDTO = new WriterDTO();
        writerDTO.setId(writer.getId());
        writerDTO.setIdCard(writer.getIdCard());
        writerDTO.setFirstName(writer.getFirstName());
        writerDTO.setLastName(writer.getLastName());
        writerDTO.setActive(writer.isActive());
        writerDTO.setEmail(writer.getEmail());

        return writerDTO;
    }


}
