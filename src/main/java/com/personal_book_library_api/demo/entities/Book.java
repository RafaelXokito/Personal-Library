package com.personal_book_library_api.demo.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String content;

    @OneToMany(mappedBy = "currentBook")
    private List<Reader> currentReaders;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<ReaderBook> readers = new ArrayList<>();

    @NotNull
    @ManyToOne
    private Writer writer;

    public Book() {
    }

    public Book(@NotNull String title, @NotNull String description, @NotNull String content, @NotNull Writer writer) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.writer = writer;

        this.readers = new ArrayList<>();
        this.currentReaders = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Reader> getCurrentReaders() {
        return currentReaders;
    }

    public List<ReaderBook> getReaders() {
        return readers;
    }

    public void addReaderBook(ReaderBook readerBook) {
        this.readers.add(readerBook);
    }

    public void setCurrentReaders(List<Reader> currentReaders) {
        this.currentReaders = currentReaders;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public Writer getWriter() {
        return writer;
    }

    public void removeReader(Reader reader) {
        this.readers.remove(reader);
    }

    public void addCurrentReader(Reader reader) {
        this.currentReaders.add(reader);
    }

    public void removeCurrentReader(Reader reader) {
        this.currentReaders.remove(reader);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", currentReaders=" + currentReaders +
                ", readers=" + readers +
                ", writer=" + writer +
                '}';
    }
}
