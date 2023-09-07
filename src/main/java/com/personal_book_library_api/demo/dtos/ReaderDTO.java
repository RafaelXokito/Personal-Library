package com.personal_book_library_api.demo.dtos;

import java.util.List;

public class ReaderDTO {

    private Long id;
    private String idCard;
    private int fontSize;
    private BookDTO currentBooks;
    private List<BookDTO> books;
    private String firstName;
    private String lastName;
    private boolean active;
    private String email;

    public ReaderDTO() {
    }

    public ReaderDTO(Long id, String idCard, int fontSize, BookDTO currentBooks, List<BookDTO> books, String firstName, String lastName, boolean active, String email) {
        this.id = id;
        this.idCard = idCard;
        this.fontSize = fontSize;
        this.currentBooks = currentBooks;
        this.books = books;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.email = email;
    }


    public ReaderDTO(Long id, String idCard, int fontSize, BookDTO currentBooks, String firstName, String lastName, boolean active, String email) {
        this.id = id;
        this.idCard = idCard;
        this.fontSize = fontSize;
        this.currentBooks = currentBooks;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public BookDTO getCurrentBooks() {
        return currentBooks;
    }

    public void setCurrentBooks(BookDTO currentBooks) {
        this.currentBooks = currentBooks;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
