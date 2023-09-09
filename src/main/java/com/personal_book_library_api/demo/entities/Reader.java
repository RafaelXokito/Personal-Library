package com.personal_book_library_api.demo.entities;

import java.util.*;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Reader extends User {
    
    @Column(nullable = false, unique = true)
    private String idCard;

    private int fontSize = 12;

    @ManyToOne
    private Book currentBook;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    private List<ReaderBook> books = new ArrayList<>();


    public Reader(@NotNull String firstName, @NotNull String lastName, @NotNull String email, @NotNull String password) {
        super(firstName, lastName, email, password);
        currentBook = null;
        books = new ArrayList<>();
    }

    @PrePersist
    private void prePersist() {
        this.idCard = "W-" + UUID.randomUUID().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("READER"));
    }

    public void addReaderBook(ReaderBook book) {
        this.books.add(book);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }
}
