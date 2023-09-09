package com.personal_book_library_api.demo.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    public Book(@NotNull String title, @NotNull String description, @NotNull String content, @NotNull Writer writer) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.writer = writer;

        this.readers = new ArrayList<>();
        this.currentReaders = new ArrayList<>();
    }

    public void addReaderBook(ReaderBook readerBook) {
        this.readers.add(readerBook);
    }

    public void removeReaderBook(ReaderBook readerBook) {
        this.readers.remove(readerBook);
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
