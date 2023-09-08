package com.personal_book_library_api.demo.dtos;

public class ReaderBookDTO {
    private Long id;
    private ReaderDTO reader;
    private BookDTO book;
    private int currentPage;

    public ReaderBookDTO() {
    }

    public ReaderBookDTO(Long id, ReaderDTO reader, BookDTO book, int currentPage) {
        this.id = id;
        this.reader = reader;
        this.book = book;
        this.currentPage = currentPage;
    }

    public ReaderBookDTO(ReaderDTO reader, BookDTO book, int currentPage) {
        this.reader = reader;
        this.book = book;
        this.currentPage = currentPage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReaderDTO getReader() {
        return reader;
    }

    public void setReader(ReaderDTO reader) {
        this.reader = reader;
    }

    public BookDTO getBook() {
        return book;
    }

    public void setBook(BookDTO book) {
        this.book = book;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
