package com.personal_book_library_api.demo.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    private String content;

    @OneToMany(mappedBy = "currentBook")
    private List<Reader> currentReaders;

    @ManyToMany
    private List<Reader> readers;

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

    public List<Reader> getReaders() {
        return readers;
    }

    public void setReaders(List<Reader> readers) {
        this.readers = readers;
    }

    public Writer getWriter() {
        return writer;
    }

    public void addReader(Reader reader) {
        this.readers.add(reader);
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
}
