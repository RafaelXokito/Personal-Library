package com.personal_book_library_api.demo.rest;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.personal_book_library_api.demo.dtos.*;
import com.personal_book_library_api.demo.entities.Book;
import com.personal_book_library_api.demo.entities.Reader;
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
import java.util.stream.Collectors;

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
        return ResponseEntity.ok(convertToDTOList(books));
    }

    @GetMapping("/books/search")
    public ResponseEntity<List<BookSimpleDTO>> search(@RequestParam(required = false, defaultValue = "") String title, @RequestParam(required = false, defaultValue = "") String keyword, @RequestParam(required = false, defaultValue = "") String writerName) {
        List<Book> books = bookService.search(title, keyword, writerName);
        return ResponseEntity.ok(convertToDTOList(books));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookSingleDTO> find(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(convertBookToDTOSingle(book));
    }

    @PostMapping("/books")
    public ResponseEntity<BookDTO> save(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Book book) {
        // userDetails.getUsername() It returns the email from the authenticated user
        bookService.save(book, userDetails.getUsername());
        BookDTO bookDTO = convertBookToDTO(book);
        return ResponseEntity.ok(bookDTO);
    }

    @PatchMapping("/books/{id}/add")
    public ResponseEntity<ReaderBookDTO> addBook(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id){
        ReaderBook readerBook = bookService.addBook(userDetails.getUsername(), id);
        return ResponseEntity.ok(convertReaderBookToDTOSimple(readerBook));
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
    public ResponseEntity<List<ReaderDTO>> getReaders(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Writer writer = writerService.getWriter(userDetails.getUsername());
        Book book = bookService.findById(id);
        if (!book.getWriter().equals(writer)) {
            throw new RuntimeException("You are not the writer of this book");
        }
        List<Reader> readers = bookService.getReaders(id);
        return ResponseEntity.ok(readers.stream()
                .map(this::convertReaderToDTOSimple)
                .collect(Collectors.toList()));
    }

    @GetMapping("/books/{id}/currentreaders")
    public ResponseEntity<List<ReaderDTO>> getCurrentReaders(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Writer writer = writerService.getWriter(userDetails.getUsername());
        Book book = bookService.findById(id);
        if (!book.getWriter().equals(writer)) {
            throw new RuntimeException("You are not the writer of this book");
        }
        List<Reader> readers = bookService.getCurrentReaders(id);
        return ResponseEntity.ok(readers.stream()
                .map(this::convertReaderToDTOSimple)
                .collect(Collectors.toList()));
    }

    @GetMapping("/books/{id}/writer")
    public ResponseEntity<WriterDTO> getWriter(@PathVariable Long id) {
        Writer writer = bookService.getWriter(id);
        return ResponseEntity.ok(convertWriterToDTOSimple(writer));
    }

    public ReaderBookDTO convertReaderBookToDTOSimple(ReaderBook readerBook) {
        ReaderBookDTO readerBookDTO = new ReaderBookDTO();
        readerBookDTO.setId(readerBook.getId());
        readerBookDTO.setBook(convertBookToDTOSimple(readerBook.getBook()));
        readerBookDTO.setCurrentPage(readerBook.getCurrentPage());
        return readerBookDTO;
    }

    public List<BookSimpleDTO> convertToDTOList(List<Book> books) {
        return books.stream()
                .map(this::convertBookToDTOSimple)
                .collect(Collectors.toList());
    }

    public BookSimpleDTO convertBookToDTOSimple(Book book) {
        BookSimpleDTO bookDTO = new BookSimpleDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setWriterName(book.getWriter().getFirstName() + " " + book.getWriter().getLastName());

        return bookDTO;
    }

    public BookSingleDTO convertBookToDTOSingle(Book book) {
        BookSingleDTO bookDTO = new BookSingleDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setContent(book.getContent());

        // Convert the Writer entity to WriterDTO
        if (book.getWriter() != null) {
            bookDTO.setWriter(convertWriterToDTOSimple(book.getWriter()));  // Assuming you have a convertToDTO method for Writer
        }

        return bookDTO;
    }

    public BookDTO convertBookToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setContent(book.getContent());

        // Convert the list of Reader entities to ReaderDTOs for currentReaders
        if (book.getCurrentReaders() != null) {
            bookDTO.setCurrentReaders(book.getCurrentReaders().stream()
                    .map(this::convertReaderToDTOSimple)  // Assuming you have a convertToDTO method for Reader
                    .collect(Collectors.toList()));
        }

        // Convert the list of Reader entities to ReaderDTOs for readers
        if (book.getReaders() != null) {
            bookDTO.setReaders(book.getReaders().stream()
                    .map(readerBook -> convertReaderToDTOSimple(readerBook.getReader()))
                    .collect(Collectors.toList()));
        }

        // Convert the Writer entity to WriterDTO
        if (book.getWriter() != null) {
            bookDTO.setWriter(convertWriterToDTOSimple(book.getWriter()));  // Assuming you have a convertToDTO method for Writer
        }

        return bookDTO;
    }

    public ReaderDTO convertReaderToDTOSimple(Reader reader) {
        return getReaderDTO(reader);
    }

    static ReaderDTO getReaderDTO(Reader reader) {
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

    public WriterDTO convertWriterToDTOSimple(Writer writer) {
        return getWriterDTO(writer);
    }

    static WriterDTO getWriterDTO(Writer writer) {
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
