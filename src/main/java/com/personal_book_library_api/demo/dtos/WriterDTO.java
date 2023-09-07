package com.personal_book_library_api.demo.dtos;

import java.util.List;

public class WriterDTO {

    private Long id;
    private String idCard;
    private List<BookDTO> books;
    private String firstName;
    private String lastName;
    private boolean active;
    private String email;

    public WriterDTO() {
    }

    public WriterDTO(Long id, String idCard, List<BookDTO> books, String firstName, String lastName, boolean active, String email) {
        this.id = id;
        this.idCard = idCard;
        this.books = books;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
        this.email = email;
    }

    public WriterDTO(Long id, String idCard, String firstName, String lastName, boolean active, String email) {
        this.id = id;
        this.idCard = idCard;
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
