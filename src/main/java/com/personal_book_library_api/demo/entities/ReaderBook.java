package com.personal_book_library_api.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ReaderBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private int currentPage;

    public ReaderBook(Reader reader, Book book, Integer currentPage) {
        this.reader = reader;
        this.book = book;
        this.currentPage = currentPage;
    }
}
