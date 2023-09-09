package com.personal_book_library_api.demo.entities;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Writer extends User {

    @Column(nullable = false, unique = true)
    private String idCard;

    @OneToMany(mappedBy = "writer")
    private List<Book> books;


    public Writer(@NotNull String firstName, @NotNull String lastName, @NotNull String email, @NotNull String password) {
        super(firstName, lastName, email, password);
        this.books = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("WRITER"));
    }

    @PrePersist
    private void prePersist() {
        this.idCard = "W-" + UUID.randomUUID().toString();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }
}
