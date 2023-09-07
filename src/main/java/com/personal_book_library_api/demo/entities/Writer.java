package com.personal_book_library_api.demo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Writer extends User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long idCard;

    @OneToMany(mappedBy = "writer")
    private List<Book> books;

    public Writer() {
    }

    public Writer(@NotNull String firstName, @NotNull String lastName, @NotNull String email, @NotNull String password) {
        super(firstName, lastName, email, password);
        this.books = new ArrayList<>();
    }

    public Long getIdCard() {
        return idCard;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }
}
