package com.personal_book_library_api.demo.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;

@Entity
public class Reader extends User {
    
    @Column(nullable = false, unique = true)
    private String idCard;

    @Value("${default-font-size:12}")
    private int fontSize;

    @ManyToOne
    private Book currentBook;

    @ManyToMany
    private List<Book> books;

    public Reader() {
    }

    public Reader(@NotNull String firstName, @NotNull String lastName, @NotNull String email, @NotNull String password) {
        super(firstName, lastName, email, password);
        currentBook = null;
        books = new ArrayList<>();
    }

    @PrePersist
    private void prePersist() {
        this.idCard = "W-" + UUID.randomUUID().toString();
    }

    public String getIdCard() {
        return idCard;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public Book getCurrentBook() {
        return currentBook;
    }

    public void setCurrentBook(Book currentBook) {
        this.currentBook = currentBook;
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
